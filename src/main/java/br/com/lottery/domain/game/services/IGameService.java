package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;

import java.util.Optional;

public interface IGameService {

    Task processUpdateGames() ;

    Optional<Game> getGameByNumberAndType(final String type, final Integer number);


}
