package com.justeattakeaway.challenge.service;

import com.justeattakeaway.challenge.model.GameMessage;
import com.justeattakeaway.challenge.model.Type;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessagingReceiverService {

    private final GameService gameService;

    public MessagingReceiverService(GameService gameService) {
        this.gameService = gameService;
    }

    @KafkaListener(topics = "${player.topic.name}")
    public void receiveMessage(GameMessage message) {
        gameService.play(message.getNumber(), Type.AUTOMATIC);
    }
}
