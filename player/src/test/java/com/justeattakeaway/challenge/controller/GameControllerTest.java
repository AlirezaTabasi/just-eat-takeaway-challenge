package com.justeattakeaway.challenge.controller;

import com.justeattakeaway.challenge.exception.GameAlreadyRunningException;
import com.justeattakeaway.challenge.model.Type;
import com.justeattakeaway.challenge.repository.GameRepository;
import com.justeattakeaway.challenge.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Value("${player.name}")
    private String playerName;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;
    @MockBean
    private GameRepository gameRepository;

    @Test
    public void start_succeeds() throws Exception {
        Mockito.when(gameService.start(Type.AUTOMATIC)).thenReturn("Game started Automatically");

        mockMvc.perform(get("/api/v1/game/start").param("type", Type.AUTOMATIC.name()))
                .andExpect(status().isOk())
                .andExpect(content().string("Game started Automatically"));
    }

    @Test
    public void start_fails_when_game_is_already_running() throws Exception {
        String errorMsg = "AUTOMATIC -> Creating a new game is not possible when a running one exists.";
        Mockito.when(gameService.start(Type.AUTOMATIC))
                .thenThrow(new GameAlreadyRunningException(errorMsg));

        mockMvc.perform(get("/api/v1/game/start").param("type", Type.AUTOMATIC.name()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMsg));
    }
}
