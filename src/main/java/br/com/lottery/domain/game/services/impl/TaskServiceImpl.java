package br.com.lottery.domain.game.services.impl;

import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import br.com.lottery.domain.game.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }
}
