package com.justeattakeaway.challenge.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("Game")
public class Game {
    @Id
    String playerName;
    Status status;

    public Game(String playerName, Status status) {
        this.playerName = playerName;
        this.status = status;
    }
}
