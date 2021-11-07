package com.justeattakeaway.challenge.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootstrapAddress;

    @Value(value = "${player.topic.name}")
    private String playerTopicName;

    @Value(value = "${opponent.topic.name}")
    private String opponentTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic playerTopic() {
        return new NewTopic(playerTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic opponentTopic() {
        return new NewTopic(opponentTopicName, 1, (short) 1);
    }
}