package com.justeattakeaway.challenge.controller;

import com.justeattakeaway.challenge.model.Type;
import com.justeattakeaway.challenge.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("start")
    public ResponseEntity<String> start(@RequestParam Type type) {
        return ResponseEntity.ok().body(gameService.start(type));
    }

    @GetMapping("play")
    public ResponseEntity<String> play(@RequestParam int number, @RequestParam Type type) {
        return ResponseEntity.ok().body(gameService.play(number, type));
    }
}
