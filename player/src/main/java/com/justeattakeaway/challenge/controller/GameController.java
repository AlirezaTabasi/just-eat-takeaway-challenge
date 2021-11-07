package com.justeattakeaway.challenge.controller;

import com.justeattakeaway.challenge.exception.GameAlreadyRunningException;
import com.justeattakeaway.challenge.model.Type;
import com.justeattakeaway.challenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("start")
    public ResponseEntity<String> start(@RequestParam Type type) throws GameAlreadyRunningException {
        return ResponseEntity.ok().body(gameService.start(type));
    }

    @GetMapping("play")
    public ResponseEntity<String> play(@RequestParam int number, @RequestParam Type type) {
        return ResponseEntity.ok().body(gameService.play(number, type));
    }

    @ExceptionHandler({GameAlreadyRunningException.class})
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
