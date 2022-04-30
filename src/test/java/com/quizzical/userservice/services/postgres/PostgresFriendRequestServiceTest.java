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
    private Player p1, p2, p3;

    @BeforeEach
    void setUp() {
        p1 = new Player("yigit", "yigit@gmail.com", "123");
        p2 = new Player("ege", "ege@gmail.com", "123");
        p3 = new Player("ouz", "ouz@gmail.com", "123");
        playerRepository.saveAllAndFlush(new ArrayList<>() {{ add(p1); add(p2); add(p3); }});

        FriendRequest f1 = new FriendRequest(p1, p2, false);
        FriendRequest f2 = new FriendRequest(p2, p3, true);
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

        Boolean result = postgresFriendRequestService.sendFriendRequest(p1.getUsername(), p3.getUsername());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p3, p1).orElse(null);

        assertTrue(result);
        assertNotNull(saved);
        assertEquals(fr.getSender(), saved.getSender());
        assertEquals(fr.getReceiver(), saved.getReceiver());
        assertFalse(saved.getAccepted());
    }

    @Test
    void cannotCreateFriendRequestAlreadyExists() {
        FriendRequest fr = new FriendRequest(p1, p2, true);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p1.getUsername(), p2.getUsername());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p2, p1).orElse(null);

        assertNotNull(saved);
        assertFalse(saved.getAccepted());
        assertFalse(result);
    }

    @Test
    void cannotCreateFriendRequestAlreadyFriends() {
        FriendRequest fr = new FriendRequest(p3, p2, false);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p3.getUsername(), p2.getUsername());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p3, p2).orElse(null);

        assertNotNull(saved);
        assertTrue(saved.getAccepted());
        assertFalse(result);
    }

    @Test
    void cannotCreateFriendRequestBetweenSamePlayersOrder() {
        FriendRequest fr = new FriendRequest(p2, p1, true);
        Boolean result = postgresFriendRequestService.sendFriendRequest(p2.getUsername(), p1.getUsername());

        FriendRequest saved = friendRepository.findByReceiverAndSender(p2, p1).orElse(null);

        assertNotNull(saved);
        assertFalse(saved.getAccepted());
        assertFalse(result);
    }


    @Test
    void respondToFriendRequestNegativeSuccessful() {
        // friend request from p2 to p3

        FriendRequest fr = friendRepository.findByReceiverAndSender(p3, p2).orElse(null);
        assertNotNull(fr);

        Boolean result = postgresFriendRequestService.respondToFriendRequest(p3.getUsername(), p2.getUsername(), false);
        assertTrue(result);

        fr = friendRepository.findByReceiverAndSender(p3, p2).orElse(null);
        assertNull(fr);
    }

    @Test
    void respondToFriendRequestPositiveSuccessful() {
        Boolean response = true;

        FriendRequest fr = friendRepository.findByReceiverAndSender(p3, p2).orElse(null);
        assertNotNull(fr);

        Boolean result = postgresFriendRequestService.respondToFriendRequest(p3.getUsername(), p2.getUsername(), response);
        assertTrue(result);

        fr = friendRepository.findByReceiverAndSender(p3, p2).orElse(null);
        assertNotNull(fr);
        assertTrue(fr.getAccepted());
        assertTrue(p2.getFriends().size() > 0);
        assertTrue(p3.getFriends().size() > 0);
    }

    @Test
    void respondToFriendRequestNoRequestFound() {
        Boolean result = postgresFriendRequestService.respondToFriendRequest(p3.getUsername(), p1.getUsername(), true);
        assertFalse(result);

        assertEquals(p1.getFriends().size(), 0);

    }
}