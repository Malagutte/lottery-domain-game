package br.com.lottery.domain.game.services.impl;

import br.com.lottery.domain.game.dtos.request.TypeDataRequestDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import br.com.lottery.domain.game.services.IGameService;
import br.com.lottery.domain.game.threads.UpdateProcessByTypeThread;
import br.com.lottery.domain.game.threads.UpdateProcessThread;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GameServiceImpl implements IGameService {

    @Autowired
    private IGameRepository gameRepository;

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Task processUpdateGames() {
        var id = UUID.randomUUID().toString();
        var task = taskRepository.save(Task.builder().id(id).initDate(LocalDateTime.now()).build());
        var asyncProcess = new UpdateProcessThread(gameRepository, taskRepository, restTemplate, id);
        var mainProcessThread = new Thread(asyncProcess);
        mainProcessThread.start();
        return task;
    }

    @Override
    public Optional<Game> getGameByNumberAndType(String type, Integer number) {
        return gameRepository.findByTypeAndNumber(type.toLowerCase(), number);
    }



}
