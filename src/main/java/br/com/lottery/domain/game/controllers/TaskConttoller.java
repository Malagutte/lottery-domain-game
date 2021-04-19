package br.com.lottery.domain.game.controllers;

import br.com.lottery.domain.game.dtos.GameDTO;
import br.com.lottery.domain.game.services.ITaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
@Api(tags = {"Task"})
@Log4j2
public class TaskConttoller {

    @Autowired
    private ITaskService taskService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = GameDTO.class)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getById(@PathVariable String id) {
        try {
            var taskOptional = taskService.findById(id);
            if (!taskOptional.isPresent()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(taskOptional.get().toDTO());
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }
}
