package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.dtos.response.CaixaResponseDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private IGameService gameService;

    @MockBean
    private IGameRepository gameRepository;

    @MockBean
    private ITaskRepository taskRepository;

    @MockBean
    private RestTemplate restTemplate;

    private static final String URL_CAIXA_BASE = "http://loterias.caixa.gov.br/wps/portal/loterias/landing";


    @Test
    public void whenUpdateShouldNotThrowException() throws InterruptedException, JsonProcessingException {

        var mega = CaixaResponseDTO.builder().numero(1).tipoJogo("mega").listaDezenas(Arrays.asList("2", "3")).dataApuracao("16/01/2021").build();
        Mockito.when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(ResponseEntity.ok().body(mega.toJson()));


        var task = gameService.processUpdateGames();
        Thread.sleep(2000L);
    }


    @Test
    public void whenSearchByTypeAndNumberShouldReturnOptional() {
        Mockito.doReturn(Optional.of(Game.builder().id(UUID.randomUUID().toString()).number(1).type("megasena").build()))
                .when(gameRepository).findByTypeAndNumber(any(), any());
        var game = gameService.getGameByNumberAndType("megasena", 1);
    }
}
