package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface IGameService {

    Task processUpdateGames() throws JsonProcessingException, InterruptedException, Exception;

    Optional<Game> getGameByNumberAndType(final String type, final Integer number);


}
