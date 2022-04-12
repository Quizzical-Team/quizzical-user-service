package com.tuzgen.userservice.entities;

import com.tuzgen.userservice.configuration.GamePropertiesConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player1;

    @BeforeEach
    void setUp() {
        player1 = new Player("testUser1", "testUser1@gmail.com", new BCryptPasswordEncoder().encode("1234"));
    }

    @AfterEach
    void tearDown() {
        player1 = null;
    }

    @Test
    void changeMMRBy() {
        // test the clamping mechanism
        int startingMMR = GamePropertiesConfig.startingMatchmakingRatio;
        assertEquals(player1.getMatchmakingRatio(), startingMMR);
        player1.changeMMRBy(-100);
        assertEquals(startingMMR - 100, player1.getMatchmakingRatio());
        player1.changeMMRBy(-1000);
        assertEquals(GamePropertiesConfig.minimumMatchmakingRatio, player1.getMatchmakingRatio());
        player1.changeMMRBy(10000);
        assertEquals(GamePropertiesConfig.maximumMatchmakingRatio, player1.getMatchmakingRatio());
    }
}