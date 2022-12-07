package br.com.lottery.domain.game.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/games")
public class GameController {

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

}
