package com.tuzgen.userservice.bootstrap;

import com.tuzgen.userservice.entities.User;
import com.tuzgen.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Slf4j
@Configuration
public class LoadDatabase {

    private final UserRepository repository;

    public LoadDatabase(UserRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<User> allUsers = this.repository.findAll();
        log.info("# of users: " + allUsers.size());

        log.info("Saving new user...");
        this.repository.save(User.builder()
                .userName("oguztuzgen")
                .email("oguztuzgen@gmail.com")
                .password("1231432").build());
//        this.repository.save(User.builder()
//                .userName("oguztuzgen")
//                .email("oguztuzgen@gmail.com")
//                .password("1231432").build());

        allUsers = this.repository.findAll();
        log.info("# of users: " + allUsers.size());
    }
}