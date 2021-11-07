package com.justeattakeaway.challenge.service;

import com.justeattakeaway.challenge.model.GameMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingSenderService {

    private final KafkaTemplate<String, GameMessage> kafkaTemplate;

    public MessagingSenderService(KafkaTemplate<String, GameMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, GameMessage message) {
        kafkaTemplate.send(topicName, message);
    }
}
