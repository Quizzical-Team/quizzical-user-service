package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Slf4j
class FriendRequestRepositoryTest {
    Player p1 = new Player("test1", "test1@gmail.com", "123");
    Player p2 = new Player("test2", "test2@gmail.com", "123");
    Player p3 = new Player("test3", "test3@gmail.com", "123");

    FriendRequest f1 = new FriendRequest(p1, p2, false);
    FriendRequest f2 = new FriendRequest(p2, p3, false);

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private FriendRequestRepository friendRepository;

    @BeforeEach
    void setUp() {
        playerRepository.saveAllAndFlush(new ArrayList<>() {{
            add(p1); add(p2); add(p3);
        }});

        testEntityManager.persist(p1);
        testEntityManager.persist(p2);
        testEntityManager.persist(p3);

        friendRepository.saveAllAndFlush(new ArrayList<>() {{
            add(f1); add(f2);
        }});

        testEntityManager.persist(f1);
        testEntityManager.persist(f2);
    }

    @AfterEach
    void tearDown() {
//        friendRepository.deleteAll();
    }

    @Test
    void sendNewPendingFriendRequest() {
        // new pending friend request
        FriendRequest friendRequest = new FriendRequest(p1, p3, false);

        FriendRequest savedRequest = friendRepository.save(friendRequest);

        assertNotNull(savedRequest.getReceiver());
        assertNotNull(savedRequest.getSender());
        assertFalse(savedRequest.getAccepted());
    }

    @Test
    void cannotCreateSecondFriendRequestBetweenSamePlayers() {
        FriendRequest friendRequest = new FriendRequest(p1, p3, false);
        FriendRequest illegal = new FriendRequest(p1, p3, false);

        friendRequest = friendRepository.saveAndFlush(friendRequest);

        assertThrows(Exception.class, () -> {
            friendRepository.save(illegal);
            testEntityManager.flush();
        });
        assertNotNull(friendRequest);
    }

    @Test
    void cannotCreateSecondFriendRequestBetweenSamePlayersOrderIndependent() {
        FriendRequest friendRequest = new FriendRequest(p1, p3, false);
        FriendRequest illegal = new FriendRequest(p3, p1, false);

        friendRequest = friendRepository.saveAndFlush(friendRequest);

        assertThrows(Exception.class, () -> {
                    friendRepository.save(illegal);
                    testEntityManager.flush();
        });
        assertNotNull(friendRequest);
    }

    @Test
    void cannotCreatePendingRequestBetweenFriends() {
        FriendRequest friendRequest = new FriendRequest(p1, p3, true);
        FriendRequest illegal = new FriendRequest(p1, p3, false);

        friendRequest = friendRepository.saveAndFlush(friendRequest);

        assertThrows(Exception.class, () -> {
            friendRepository.save(illegal);
            testEntityManager.flush();
        });
        assertNotNull(friendRequest);
    }

    @Test
    void findByReceiverAndSenderExists() {
        FriendRequest fr = friendRepository.findByReceiverAndSender(p2.getId(), p1.getId()).orElse(null);
        assertNotNull(fr);
    }

    @Test
    void existsByReceiverAndSenderExists() {
        Boolean result = friendRepository.existsByReceiverAndSender(p2.getId(), p1.getId());
        assertTrue(result);
    }

    @Test
    void acceptPendingFriendRequest() {
        FriendRequest friendRequest = new FriendRequest(p1, p3, false);
        friendRequest = friendRepository.saveAndFlush(friendRequest);

        friendRequest.setAccepted(true);
        friendRequest = friendRepository.save(friendRequest);

        assertTrue(friendRequest.getAccepted());
        assertEquals(p1, friendRequest.getSender());
        assertEquals(p3, friendRequest.getReceiver());
    }
}