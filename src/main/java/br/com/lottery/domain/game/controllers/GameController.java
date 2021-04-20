package br.com.lottery.domain.game.controllers;

import br.com.lottery.domain.game.dtos.GameDTO;
import br.com.lottery.domain.game.dtos.TaskDTO;
import br.com.lottery.domain.game.dtos.request.SearchRequestDTO;
import br.com.lottery.domain.game.dtos.response.SearchResponseDTO;
import br.com.lottery.domain.game.helpers.ResponseEntityHelper;
import br.com.lottery.domain.game.services.IGameService;
import br.com.lottery.domain.game.validations.GameRequestValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/game")
@Api(tags = {"Game"})
@Log4j2
public class GameController {

    @Autowired
    private IGameService gameService;

    @ApiResponses(value = {@ApiResponse(code = 202, message = "Ok", response = TaskDTO.class)})
    @PostMapping(value = "/update-database", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getNewGames() {

        try {
            var task = gameService.processUpdateGames();

            return ResponseEntity.accepted().body(task.toDTO());
        } catch (Exception e) {
            log.error(e);
            return ResponseEntityHelper.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, Optional.of(e));
        }

    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = SearchResponseDTO.class)})
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchGames(@RequestBody SearchRequestDTO searchRequest) {

        try {
            var result = gameService.searchGames(searchRequest);

            return ResponseEntityHelper.buildResponseEntity(HttpStatus.OK, result);
        } catch (Exception e) {
            log.error(e);
            return ResponseEntityHelper.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }


    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = GameDTO.class)})
    @GetMapping(value = "/{type}/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getGameByTypeAndNumber(@PathVariable String type, @PathVariable Integer number) {
        try {

            if (!GameRequestValidation.isValidGetGameRequest(type, number)) {
                return ResponseEntity.badRequest().build();
            }

            var gameOptional = gameService.getGameByNumberAndType(type, number);

            if (!gameOptional.isPresent()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntityHelper.buildResponseEntity(HttpStatus.OK, gameOptional.get().toDTO());

        } catch (Exception e) {
            log.error(e);
            return ResponseEntityHelper.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }
}
