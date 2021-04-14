package br.com.lottery.domain.game.threads;

import br.com.lottery.domain.game.dtos.request.TypeDataRequestDTO;
import br.com.lottery.domain.game.dtos.response.CaixaResponseDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.repositories.IGameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class UpdateProcessByTypeThread implements Runnable {

    private final IGameRepository gameRepository;
    private final TypeDataRequestDTO type;
    private final RestTemplate restTemplate;

    private static final String URL_CAIXA_BASE = "http://loterias.caixa.gov.br/wps/portal/loterias/landing";

    @Override
    public void run() {


        try {
            var baseUrlType = String.format("%s/%s", URL_CAIXA_BASE, type.getUrlParameters());
            var lastGame = doRequestGame(baseUrlType).getNumero();

            for (int i = lastGame; i > 0; i--) {
                var search = gameRepository.findByTypeAndNumber(type.getType().toLowerCase(), i);
                if (search.isPresent()) {
                    continue;
                }
                processByGame(type.getType(), baseUrlType, i);
            }


        } catch (Exception e) {
            log.error(e);
        }

    }


    private void processByGame(String type, String baseUrlType, Integer gameNumber) throws JsonProcessingException {
        try {
            var requestUrl = String.format("%s/%s%s", baseUrlType, "p=concurso=", gameNumber.toString());
            var requestResult = doRequestGame(requestUrl);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            var gameModel = Game.builder()
                    .id(UUID.randomUUID().toString())
                    .number(requestResult.getNumero())
                    .raffleNumbers(requestResult.getListaDezenas().stream().map(n -> Integer.valueOf(n)).collect(Collectors.toList()))
                    .type(type.toLowerCase())
                    .date(LocalDate.parse(requestResult.getDataApuracao(), formatter))
                    .build();

            gameRepository.save(gameModel);

            log.info("{} - {} saved - {}", type.toLowerCase(), requestResult.getNumero(), gameModel.toJson());

        } catch (Exception e) {

            log.error("Error to save game {} - {}", type, gameNumber);
            log.error(e);

        }
    }


    private CaixaResponseDTO doRequestGame(String url) throws JsonProcessingException {

        var json = restTemplate.getForEntity(url, String.class);

        return new ObjectMapper().readValue(json.getBody(), CaixaResponseDTO.class);
    }
}
