package br.com.lottery.domain.game.services;

import br.com.lottery.domain.game.models.Task;

import java.util.Optional;

public interface ITaskService {

    Optional<Task> findById(String id);
}
