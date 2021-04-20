package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.dtos.response.CaixaResponseDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
    @DisplayName("When update should not throw exception")
    public void whenUpdateShouldNotThrowException() throws InterruptedException, JsonProcessingException {

        var mega = CaixaResponseDTO.builder()
                .numero(1).tipoJogo("mega")
                .listaDezenas(Arrays.asList("2", "3"))
                .dataApuracao("16/01/2021").build();
        when(gameRepository.findByTypeAndNumber(eq("lotofacil"), eq(1)))
                .thenReturn(Optional.of(Game.builder().id(UUID.randomUUID().toString()).build()));
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok().body(mega.toJson()));

        Assertions.assertDoesNotThrow(() -> gameService.processUpdateGames());
        Thread.sleep(1500L);
    }


    @Test
    @DisplayName("When update and not save the game should not throw exception")
    public void whenUpdateAndNotSaveGameShouldNotThrowException() throws InterruptedException, JsonProcessingException {

        var mega = CaixaResponseDTO.builder()
                .numero(1).tipoJogo("mega")
                .listaDezenas(Arrays.asList("2", "3"))
                .dataApuracao("16/01/2021").build();

        when(gameRepository.save(any())).thenThrow(new RuntimeException());


        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException())
                .thenReturn(ResponseEntity.ok().body(mega.toJson()));
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(Task.builder().id(UUID.randomUUID().toString()).build()));

        Assertions.assertDoesNotThrow(() -> gameService.processUpdateGames());
        Thread.sleep(1500L);
    }


    @Test
    @DisplayName("When get should return optional of game")
    public void whenSearchByTypeAndNumberShouldReturnOptional() {
        when(gameRepository.findByTypeAndNumber(any(), any()))
                .thenReturn(Optional.of(Game.builder().id(UUID.randomUUID().toString()).number(1).type("megasena").build()));
        var game = gameService.getGameByNumberAndType("megasena", 1);
        Assertions.assertFalse(game.isEmpty());
    }
}
