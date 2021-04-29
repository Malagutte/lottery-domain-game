package br.com.lottery.domain.game.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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

import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.services.ITaskService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskConttoller.class)
@ContextConfiguration
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTest {

    @MockBean
    private ITaskService taskService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    @DisplayName("when task not exists should return code 204")
    public void whenIdNotExistsShouldReturnCode204() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.doReturn(Optional.empty())
                .when(taskService)
                .findById(id);
        var requestBuilder = MockMvcRequestBuilders.get(String.format("/api/v1/task/%s", id));
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());

    }


    @Test
    @DisplayName("when task exists should return code 200")
    public void whenIdExistsShouldReturnCode200() throws Exception {
        var id = UUID.randomUUID().toString();
        Mockito.doReturn(Optional.of(Task.builder().id(id)
                .initDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMinutes(30))
                .build()))
                .when(taskService).findById(id);
        var requestBuilder = MockMvcRequestBuilders.get(String.format("/api/v1/task/%s", id));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }


    @Test
    @DisplayName("when throws exception should return code 500")
    public void whenThrowsErrorShouldReturnCode500() throws Exception {
        var id = UUID.randomUUID().toString();
        Mockito.doThrow(new RuntimeException())
                .when(taskService).findById(id);
        var requestBuilder = MockMvcRequestBuilders.get(String.format("/api/v1/task/%s", id));
        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

}
