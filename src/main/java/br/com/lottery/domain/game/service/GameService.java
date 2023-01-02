package br.com.lottery.domain.game.service;

import br.com.lottery.domain.game.entities.GameEntity;
import br.com.lottery.domain.game.models.GameDomainModel;
import br.com.lottery.domain.game.models.TaskDomainModel;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import br.com.lottery.domain.game.service.definitions.ICaixaService;
import br.com.lottery.domain.game.service.definitions.IGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IGameRepository gameRepository;

    private final ITaskRepository taskRepository;
    private final ICaixaService caixaGovService;


    @Override
    public void batchProcess() {
        log.info("STARTING BATCH PROCESS");
        var taskDomainModel = new TaskDomainModel(taskRepository);

        batchByTypeProcess();

        taskDomainModel.finish();

        log.info("FINISHING BATCH PROCESS");

    }

    @Override
    public GameEntity getGameByNumberAndType(String gameType, Integer gameNumber) {
        return gameRepository
                .findByTypeAndNumber(gameType, gameNumber)
                .orElseGet(() -> {
                    var gameDetails = caixaGovService.getGameDetailsByNumberAndType(gameType, gameNumber);
                    var gameDomainModel = new GameDomainModel(gameDetails, gameRepository);
                    return gameDomainModel.create();
                });
    }

    @Override
    public Collection<GameEntity> getGamesByTypeAndDate(LocalDate from, LocalDate to, String gameType) {
        return gameRepository.findByTypeAndDateBetween(gameType, from, to);
    }

    private void batchByTypeProcess() {
        List.of("megasena").forEach(gameType -> {
            log.info("PROCESSING GAME TYPE: {}", gameType);
            var lastGame = caixaGovService.getLastGameDetailsByType(gameType);

            for (var i = lastGame.getNumero(); i > 0; i--) {
                batchSaveProcess(gameType, i);
            }

            log.info("GAME TYPE {} PROCESSED", gameType);
        });
    }

    private void batchSaveProcess(String gameType, Integer i) {
        var isGameAlreadySaved = gameRepository.findByTypeAndNumber(gameType, i).isPresent();

        if (isGameAlreadySaved) {
            return;
        }

        var gameDetails = caixaGovService.getGameDetailsByNumberAndType(gameType, i);
        var gameDomainModel = new GameDomainModel(gameDetails, gameRepository);

        gameDomainModel.create();
        log.info("GAME {} NUMBER: {} SAVED", gameDomainModel.getGameType(), gameDomainModel.getGameNumber());
    }

}
