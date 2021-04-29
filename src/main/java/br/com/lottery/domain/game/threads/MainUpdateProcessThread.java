package br.com.lottery.domain.game.threads;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import br.com.lottery.domain.game.dtos.request.TypeDataRequestDTO;
import br.com.lottery.domain.game.repositories.IGameRepository;
import br.com.lottery.domain.game.repositories.ITaskRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class MainUpdateProcessThread implements Runnable{

    private IGameRepository gameRepository;
    private ITaskRepository taskRepository;
    private RestTemplate restTemplate;
    private String taskId;

    @SneakyThrows
    @Override
    public void run() {

        log.info("============== Start update process ==============");
        var types = getTypesForRequest();

        var processTypesAsync = types.stream().map(t -> {
            var runnable = new UpdateProcessByTypeThread(gameRepository, t, restTemplate);
            var threadName = UUID.randomUUID().toString().concat("_").concat(t.getType());
            return new Thread(runnable, threadName);
        }).collect(Collectors.toList());

        processTypesAsync.forEach(Thread::run);

        for (Thread thread : processTypesAsync) {
            thread.join();
        }

        var taskOpt = taskRepository.findById(taskId);
        
        if(taskOpt.isPresent()){
            taskOpt.get().setEndDate(LocalDateTime.now());
            taskRepository.save(taskOpt.get());
        }

        log.info("============== End update process ==============");

    }

    private List<TypeDataRequestDTO> getTypesForRequest() {
        return Arrays.asList(TypeDataRequestDTO.builder()
                        .type("megasena")
                        .urlParameters("megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado")
                        .build(),
                TypeDataRequestDTO.builder().type("lotofacil")
                        .urlParameters("lotofacil/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbz8vTxNDRy9_Y2NQ13CDA0sTIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wBmoxN_FydLAGAgNTKEK8DkRrACPGwpyQyMMMj0VAcySpRM!/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_61L0H0G0J0VSC0AC4GLFAD2003/res/id=buscaResultado").
                        build());
    }
}
