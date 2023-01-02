package br.com.lottery.domain.game.service.definitions;

import br.com.lottery.domain.game.entities.GameEntity;

import java.time.LocalDate;
import java.util.Collection;

public interface IGameService {
    void batchProcess();
    GameEntity getGameByNumberAndType(String gameType, Integer gameNumber);
    Collection<GameEntity> getGamesByTypeAndDate(LocalDate toDate, LocalDate fromDate, String gameType);
}
