package br.com.lottery.domain.game.services.impl;

import br.com.lottery.domain.game.dtos.request.TypeDataRequestDTO;
import br.com.lottery.domain.game.models.Game;
import br.com.lottery.domain.game.models.Task;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import br.com.lottery.domain.game.services.IGameService;
import br.com.lottery.domain.game.threads.UpdateProcessByTypeThread;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public Task processUpdateGames() throws Exception {
        Task task = taskRepository.save(Task.builder().id(UUID.randomUUID().toString()).initDate(LocalDateTime.now()).build());
        var asyncProcess = new Runnable() {
            @Override
            public void run() {
                try {
                    updateProcess(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        var mainProcessThread = new Thread(asyncProcess);
        mainProcessThread.start();
        return task;
    }

    @Override
    public Optional<Game> getGameByNumberAndType(String type, Integer  number) {
        return gameRepository.findByTypeAndNumber(type.toLowerCase(), number);
    }


    private List<TypeDataRequestDTO> getTypesForRequest() throws JsonProcessingException {
        return Arrays.asList(TypeDataRequestDTO.builder()
                        .type("megasena")
                        .urlParameters("megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado")
                        .build(),
                TypeDataRequestDTO.builder().type("lotofacil")
                        .urlParameters("lotofacil/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbz8vTxNDRy9_Y2NQ13CDA0sTIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wBmoxN_FydLAGAgNTKEK8DkRrACPGwpyQyMMMj0VAcySpRM!/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_61L0H0G0J0VSC0AC4GLFAD2003/res/id=buscaResultado").
                        build());
    }


    public void updateProcess(Task task) throws Exception {
        log.info("============== Start update process ==============");
        var types = getTypesForRequest();

        var processTypesAsync = types.stream().map(t -> {
            var runnable = new UpdateProcessByTypeThread(gameRepository, t, restTemplate);
            var threadName = UUID.randomUUID().toString().concat("_").concat(t.getType());
            return new Thread(runnable, threadName);
        }).collect(Collectors.toList());

        processTypesAsync.forEach(t -> t.start());

        processTypesAsync.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        task.setEndDate(LocalDateTime.now());
        taskRepository.save(task);
        log.info("============== End update process ==============");
    }


}
