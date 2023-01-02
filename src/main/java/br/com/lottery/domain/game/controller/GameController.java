package br.com.lottery.domain.game.controller;

import br.com.lottery.domain.game.entities.GameEntity;
import br.com.lottery.domain.game.service.definitions.IGameService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
public class GameController {

    private final IGameService gameService;


    @GetMapping(name = "/find", produces = APPLICATION_JSON_VALUE)
    public GameEntity getGameByNumberAndType(
            @RequestParam()
            String gameType,
            @RequestParam()
            String gameNumber
    ) {
        var entity = gameService.getGameByNumberAndType(gameType, Integer.valueOf(gameNumber));
        return entity;

    }

//    @GetMapping(name = "/{gameType}/{from}/{to}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<GameEntity>> getByTypeBetweenDates(
//            @PathVariable
//            @NotBlank(message = "Game type is required")
//            String gameType,
//
//            @PathVariable
//            @NotNull(message = "From date is required")
//            LocalDate from,
//
//            @PathVariable
//            @NotNull(message = "To date is required")
//            LocalDate to
//    ) {
//        var entity = gameService.getGamesByTypeAndDate(from, to, gameType);
//        return ResponseEntity.ok(entity);
//    }

    @PostMapping(name = "/trigger", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getGameByNumberAndType() {
        gameService.batchProcess();
        return ResponseEntity.ok().build();
    }

}
