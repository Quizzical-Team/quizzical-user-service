package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.configuration.GamePropertiesConfig;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostgresPlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private PostgresPlayerService playerService;

    private Player player1;
    private Player player2;
    private List<Player> players;

    private final int minMMR = GamePropertiesConfig.minimumMatchmakingRatio;
    private final int maxMMR = GamePropertiesConfig.maximumMatchmakingRatio;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder(11);
        player1 = new Player("testUser1", "testUser1@gmail.com", passwordEncoder.encode("1234"));
        player2 = new Player("testUser2", "testUser2@gmail.com", passwordEncoder.encode("1234"));
        players = new ArrayList<>() {{
            add(player1);
            add(player2);
        }};
    }

    @AfterEach
    public void tearDown() {
        player1 = player2 = null;
        players = null;
    }

    @Test
    @DisplayName("Test create single user success")
    void createUser() {
        when(playerRepository.save(any())).thenReturn(player1);
        playerService.addUser(player1);
        verify(playerRepository, times(1)).save(any());
    }

    @Test
    @Disabled
    void fluctuateMMR() {
        when(playerRepository.save(any())).thenReturn(player1);
        Assertions.assertTrue(player1.getMatchmakingRatio() >= minMMR && player1.getMatchmakingRatio() <= maxMMR);
    }

    @Test
    @Disabled
    void fluctuateMMRBatch() {
    }
}