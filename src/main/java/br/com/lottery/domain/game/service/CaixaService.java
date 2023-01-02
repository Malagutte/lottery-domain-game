package br.com.lottery.domain.game.service;

import br.com.lottery.domain.game.dtos.response.CaixaGovResponse;
import br.com.lottery.domain.game.service.definitions.ICaixaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
@Slf4j
public class CaixaService implements ICaixaService {

    private final RestTemplate restTemplate;

    @Override
    public CaixaGovResponse getGameDetailsByNumberAndType(String type, Integer number) {
        return requestToCaixaGov(type, number);
    }


    @Override
    public CaixaGovResponse getLastGameDetailsByType(String type) {
        return requestToCaixaGov(type, null);
    }


    private CaixaGovResponse requestToCaixaGov(String type, Integer number) {
        StringBuilder url = new StringBuilder("https://servicebus2.caixa.gov.br/portaldeloterias/api")
                .append("/")
                .append(type);

        if (number != null) {
            url.append("/").append(number);
        }

        log.debug("URL: {}", url);

        return restTemplate.getForEntity(url.toString(), CaixaGovResponse.class).getBody();
    }

}
