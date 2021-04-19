package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private IGameService gameService;

    @MockBean
    private IGameRepository gameRepository;

    @MockBean
    private ITaskRepository taskRepository;


    @Test
    public void whenUpdateShouldNotThrowException() {

        Assertions.assertDoesNotThrow(() -> gameService.processUpdateGames());
    }


    @Test
    public void whenSearchByTypeAndNumberShouldReturnOptional() {
        Mockito.doReturn(Optional.of(Game.builder().id(UUID.randomUUID().toString()).number(1).type("megasena").build()))
                .when(gameRepository).findByTypeAndNumber(any(), any());
        var game = gameService.getGameByNumberAndType("megasena", 1);
    }
}
