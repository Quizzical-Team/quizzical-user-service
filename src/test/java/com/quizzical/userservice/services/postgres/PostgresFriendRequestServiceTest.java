package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.repositories.FriendRequestRepository;
import com.quizzical.userservice.repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ComponentScan("com.quizzical.userservice.services")
@ComponentScan("com.quizzical.userservice.repositories")
@Slf4j
class PostgresFriendRequestServiceTest {
    @Autowired
    private FriendRequestRepository friendRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    @InjectMocks
    private PostgresFriendRequestService postgresFriendRequestService;
    private FriendRequest f1, f2;
    private Player p1, p2, p3;

    @BeforeEach
    void setUp() {
        p1 = new Player("yigit", "yigit@gmail.com", "123");
        p2 = new Player("ege", "ege@gmail.com", "123");
        p3 = new Player("ouz", "ouz@gmail.com", "123");
        playerRepository.saveAllAndFlush(new ArrayList<>() {{ add(p1); add(p2); add(p3); }});

        f1 = new FriendRequest(p1, p2, false);
        f2 = new FriendRequest(p2, p3, true);
        friendRepository.saveAndFlush(f1);
        friendRepository.saveAndFlush(f2);
    }

    @AfterEach
    void tearDown() {
        playerRepository.deleteAll();
    }

    @Test
    void createFriendRequestSuccess() {
        FriendRequest fr = new FriendRequest(p1, p3, false);

        Boolean result = postgresFriendRequestService.sendFriendRequest(p1.getId(), p3.getId());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p1.getId(), p3.getId()).orElse(null);

        assertTrue(result);
        assertNotNull(saved);
        assertEquals(fr.getSender(), saved.getSender());
        assertEquals(fr.getReceiver(), saved.getReceiver());
        assertFalse(saved.getAccepted());
    }

    @Test
    void cannotCreateFriendRequestAlreadyExists() {
        FriendRequest fr = new FriendRequest(p1, p2, true);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p1.getId(), p2.getId());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p1.getId(), p3.getId()).orElse(null);

        assertNotNull(saved);
        assertFalse(saved.getAccepted());
        assertFalse(result);
    }

    @Test
    void cannotCreateFriendRequestAlreadyFriends() {
        FriendRequest fr = new FriendRequest(p3, p2, false);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p3.getId(), p2.getId());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p2.getId(), p3.getId()).orElse(null);

        assertNotNull(saved);
        assertTrue(saved.getAccepted());
        assertFalse(result);
    }

    @Test
    void cannotCreateFriendRequestBetweenSamePlayersOrder() {
        FriendRequest fr = new FriendRequest(p2, p1, true);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p2.getId(), p1.getId());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p1.getId(), p2.getId()).orElse(null);

        assertNotNull(saved);
        assertFalse(saved.getAccepted());
        assertFalse(result);
    }










    @Test
    @Disabled
    void respondToFriendRequestNegativeSuccessful() {

    }

    @Test
    @Disabled
    void respondToFriendRequestPositiveSuccessful() {

    }

    @Test
    @Disabled
    void respondToFriendRequestNoRequestFound() {

    }
}