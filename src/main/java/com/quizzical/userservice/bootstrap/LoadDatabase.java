package com.quizzical.userservice.bootstrap;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.repositories.FriendRequestRepository;
import com.quizzical.userservice.repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@Slf4j
@Configuration
@ComponentScan("com.quizzical.userservice.repositories")
@Profile("dev")
public class LoadDatabase {

    private final PlayerRepository playerRepository;
    private final FriendRequestRepository friendRepository;

    public LoadDatabase(PlayerRepository playerRepository, FriendRequestRepository friendRepository) {
        this.playerRepository = playerRepository;
        this.friendRepository = friendRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        ArrayList<Player> players = new ArrayList<>() {{
            add(new Player(
                    "torvalds",
                    "torvalds@gmail.com",
                    new BCryptPasswordEncoder().encode("123123")));
            add(new Player("tuzgosh",
                    "tuzgosh@gmail.com",
                    new BCryptPasswordEncoder().encode("123123")));
            add(new Player("yeet",
                    "yeet@gmail.com",
                    new BCryptPasswordEncoder().encode("123123")));
        }};

        players.get(0).getFriends().add(players.get(1));
        players.get(1).getFriends().add(players.get(0));

        this.playerRepository.saveAll(players);

        Player p1 = playerRepository.findByUsername("torvalds").orElseThrow(UserNotFoundException::new);
        Player p2 = playerRepository.findByUsername("yeet").orElseThrow(UserNotFoundException::new);

        this.friendRepository.saveAll(
               new ArrayList<>() {{
                   add(FriendRequest.builder().sender(p2).receiver(p1).build());
               }}
        );

        log.info("Finished database bootstrap...");
    }
}