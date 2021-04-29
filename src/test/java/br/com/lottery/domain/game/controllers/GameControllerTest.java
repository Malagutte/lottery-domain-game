package br.com.lottery.domain.game.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.services.IGameService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@ContextConfiguration
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameControllerTest {

    @MockBean
    private IGameService gameService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    @DisplayName("when try to get game with number 0 should return bad request")
    public void shouldReturnCode400() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/game/seila/0");
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when try to get game with valid information should return ok")
    public void shouldReturnCode200() throws Exception {
        Mockito.doReturn(Optional.of(Game.builder().id(UUID.randomUUID().toString()).date(LocalDate.now()).build()))
                .when(gameService)
                .getGameByNumberAndType("megasena", 1);
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/game/megasena/1");
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    @DisplayName("when try to get game with valid information should return ok")
    public void shouldReturnCode204IfNotFoundGame() throws Exception {
        Mockito.doReturn(Optional.empty())
                .when(gameService)
                .getGameByNumberAndType("megasena", 1);
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/game/megasena/1");
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("when try to get game with valid information but throws a exception shhould return code 500")
    public void shouldReturnCode500() throws Exception {
        Mockito.doThrow(new RuntimeException())
                .when(gameService)
                .getGameByNumberAndType("megasena", 1);
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/game/megasena/1");
        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("when try to update data should return code 202")
    public void whenTryToUpdateDataShouldReturn202() throws Exception {
        Mockito.doReturn(Task.builder().id(UUID.randomUUID().toString()).build())
                .when(gameService).processUpdateGames();

        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/game/update-database");
        mockMvc.perform(requestBuilder).andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("when try to update data and throw error should return code 500")
    public void whenTryToUpdateDataThrowExceptionShouldReturnCode500() throws Exception {
        Mockito.doThrow(new RuntimeException())
                .when(gameService).processUpdateGames();

        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/game/update-database");
        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }
}
