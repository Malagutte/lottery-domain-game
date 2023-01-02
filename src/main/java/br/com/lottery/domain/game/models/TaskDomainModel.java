package br.com.lottery.domain.game.models;

import br.com.lottery.domain.game.entities.TaskEntity;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
@Slf4j
public class TaskDomainModel {
    private final TaskEntity entity;

    private final ITaskRepository repository;

    public TaskDomainModel(ITaskRepository repository) {
        entity = repository.save(TaskEntity.builder().id(UUID.randomUUID()).initDate(LocalDateTime.now()).build());
        this.repository = repository;
    }

    public void finish() {
        entity.setEndDate(LocalDateTime.now());
        displayDiffTimeInMinutes();
        repository.save(entity);
    }

    private void displayDiffTimeInMinutes() {

        var time = entity.getInitDate().until(entity.getEndDate(), ChronoUnit.MINUTES);
        log.info("TASK FINISHED IN {} MINUTES", time);

    }
}
