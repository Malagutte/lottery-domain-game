package br.com.lottery.domain.game.service.definitions;

import br.com.lottery.domain.game.dtos.response.CaixaGovResponse;

public interface ICaixaService {
    CaixaGovResponse getGameDetailsByNumberAndType(String type, Integer number);

    CaixaGovResponse getLastGameDetailsByType(String type);

}
