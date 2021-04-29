package br.com.lottery.domain.game.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.lottery.domain.game.dtos.request.SearchRequestDTO;
import br.com.lottery.domain.game.dtos.response.SearchResponseDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import br.com.lottery.domain.game.services.IGameService;
import br.com.lottery.domain.game.threads.MainUpdateProcessThread;

@Service
public class GameServiceImpl implements IGameService {

    @Autowired
    private IGameRepository gameRepository;

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Task processUpdateGames() {
        var id = UUID.randomUUID().toString();
        var task = taskRepository.save(Task.builder().id(id).initDate(LocalDateTime.now()).build());
        var asyncProcess = new MainUpdateProcessThread(gameRepository, taskRepository, restTemplate, id);
        var mainProcessThread = new Thread(asyncProcess);
        mainProcessThread.start();
        return task;
    }

    @Override
    public Optional<Game> getGameByNumberAndType(String type, Integer number) {
        return gameRepository.findByTypeAndNumber(type.toLowerCase(), number);
    }

    @Override
    public SearchResponseDTO searchGames(SearchRequestDTO searchRequest) {
        var games = mongoTemplate.find(searchRequest.buildQuery(), Game.class);

        return SearchResponseDTO.builder()
                .elements(games.stream().map(Game::toDTO).collect(Collectors.toList()))
                .build();
    }


}
