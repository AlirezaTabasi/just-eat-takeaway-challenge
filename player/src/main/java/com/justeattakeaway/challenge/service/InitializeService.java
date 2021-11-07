package com.justeattakeaway.challenge.service;

import com.justeattakeaway.challenge.model.Game;
import com.justeattakeaway.challenge.model.Status;
import com.justeattakeaway.challenge.repository.GameRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitializeService {
    @Value("${player.name}")
    private String playerName;

    private final GameRepository gameRepository;

    public InitializeService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void init() {
        gameRepository.save(new Game(playerName, Status.READY));
    }
}
