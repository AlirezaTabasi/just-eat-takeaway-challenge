package com.justeattakeaway.challenge.service;

import com.justeattakeaway.challenge.model.Game;
import com.justeattakeaway.challenge.model.GameMessage;
import com.justeattakeaway.challenge.model.Status;
import com.justeattakeaway.challenge.model.Type;
import com.justeattakeaway.challenge.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class GameService {
    @Value("${player.name}")
    private String playerName;
    @Value("${opponent.topic.name}")
    private String opponentTopicName;
    @Value("${opponent.port}")
    private int opponentPort;

    private final GameRepository gameRepository;
    private final MessagingSenderService messagingService;

    @Autowired
    public GameService(GameRepository gameRepository, MessagingSenderService messagingService) {
        this.gameRepository = gameRepository;
        this.messagingService = messagingService;
    }

    public String start(Type type) {
        Optional<Game> game = gameRepository.findById(playerName);

        if (game.isPresent() && game.get().getStatus() == Status.PLAY) {
            String info = String.format("%s -> Creating a new game is not possible when a running one exists.", type.name());
            log.info(info);
            return info;
        }

        int number = new Random().nextInt(1000) + 3;

        log.info(String.format("%s -> Start Game: [Number: %d]", type.name(), number));
        gameRepository.save(new Game(playerName, Status.PLAY));

        if (Type.AUTOMATIC == type) {
            messagingService.sendMessage(opponentTopicName, new GameMessage(number, type));
            return "Game started Automatically";
        }

        String manualUrl = String.format("http://127.0.0.1:%d/api/v1/game/play?number=%d&&type=MANUAL", opponentPort, number);
        String automaticUrl = String.format("http://127.0.0.1:%d/api/v1/game/play?number=%d&&type=AUTOMATIC", opponentPort, number);

        return String.format("For continuing the game call: MANUAL[ %s ] AUTOMATIC [ %s ]", manualUrl, automaticUrl);
    }

    public String play(int number, Type type) {
        Optional<Game> optionalGame = gameRepository.findById(playerName);
        if (optionalGame.isEmpty()) {
            gameRepository.save(new Game(playerName, Status.PLAY));
        }
        if (number == 1) {
            log.info(String.format("%s -> Lose!", type.name()));
            gameRepository.save(new Game(playerName, Status.READY));
            return "Lose!";
        }
        int adding = addingNumber(number);
        int result = (number + adding) / 3;

        String response = "";

        if (result == 1) {
            log.info(String.format("%s -> Win: [Receive: %d, Add: %d, Result: %d]", type.name(), number, adding, result));
            gameRepository.save(new Game(playerName, Status.READY));
            response = "Win! - ";
        } else {
            log.info(String.format("%s -> Play Game: [Receive: %d, Add: %d, Result: %d]", type.name(), number, adding, result));
        }

        if (Type.AUTOMATIC == type) {
            messagingService.sendMessage(opponentTopicName, new GameMessage(result, type));
            response += "Game Continuing Automatically";
        } else {
            String manualUrl = String.format("http://127.0.0.1:%d/api/v1/game/play?number=%d&&type=MANUAL", opponentPort, result);
            String automaticUrl = String.format("http://127.0.0.1:%d/api/v1/game/play?number=%d&&type=AUTOMATIC", opponentPort, result);

            response += String.format("For continuing the game call: MANUAL [ %s ] AUTOMATIC [ %s ]", manualUrl, automaticUrl);
        }

        return response;
    }

    private int addingNumber(int number) {
        switch (number % 3) {
            case 1:
                return -1;
            case 2:
                return 1;
        }
        return 0;
    }
}
