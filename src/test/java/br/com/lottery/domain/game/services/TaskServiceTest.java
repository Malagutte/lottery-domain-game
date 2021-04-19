package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private ITaskService taskService;

    @MockBean
    private ITaskRepository taskRepository;

    @Test
    @DisplayName("when search id doesnt exists should return empty")
    public void whenSearchIdDoesntExistsShouldReturnEmptyOptional() {
        var id = UUID.randomUUID().toString();
        Mockito.doReturn(Optional.empty())
                .when(taskRepository).findById(id);

        assertTrue("No task found", taskService.findById(id).isEmpty());
    }

    @Test
    @DisplayName("when search id does exists should return task object")
    public void whenSearchIdDoesExistsShouldReturnOptionalWithObject() {
        var id = UUID.randomUUID().toString();
        Mockito.doReturn(Optional.of(Task.builder().id(id)
                .initDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMinutes(30))
                .build()))
                .when(taskRepository).findById(id);

        assertFalse("Task exists", taskService.findById(id).isEmpty());
    }
}
