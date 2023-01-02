package br.com.lottery.domain.game.models;

import br.com.lottery.domain.game.dtos.response.CaixaAwardResponse;
import br.com.lottery.domain.game.dtos.response.CaixaGovResponse;
import br.com.lottery.domain.game.entities.AwardEntity;
import br.com.lottery.domain.game.entities.GameEntity;
import br.com.lottery.domain.game.repositories.IGameRepository;

import javax.swing.text.html.parser.Entity;
import java.util.stream.Collectors;

public class GameDomainModel {

    private final GameEntity entity;
    private final IGameRepository repository;

    public GameDomainModel(CaixaGovResponse caixaGovResponse, IGameRepository repository) {
        this.repository = repository;
        this.entity = GameEntity.builder()
                .type(caixaGovResponse.getTipoJogo())
                .number(caixaGovResponse.getNumeroJogo())
                .hitsToWin(caixaGovResponse.getListaRateioPremio()
                        .stream().map(CaixaAwardResponse::getFaixa)
                        .collect(Collectors.toList()))
                .awards(caixaGovResponse.getListaRateioPremio()
                        .stream().map(award -> AwardEntity.builder()
                                .amountPeople(award.getNumeroDeGanhadores())
                                .prize(award.getValorPremio())
                                .hitNumbers(award.getFaixa())
                                .build())
                        .collect(Collectors.toList()))
                .details(caixaGovResponse)
                .date(caixaGovResponse.getDataApuracao())
                .raffleNumbers(caixaGovResponse.getListaDezenas().stream().map(Integer::parseInt).collect(Collectors.toList()))
                .build();
    }


    public GameEntity create() {
        return repository.save(entity);
    }


    public Integer getGameNumber() {
        return entity.getNumber();
    }

    public String getGameType() {
        return entity.getType();
    }

}
