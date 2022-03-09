package com.tuzgen.userservice.repository;

import com.tuzgen.userservice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

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
        this.repository.save(User.builder()
                .userName("oguztuzgen")
                .email("oguztuzgen@gmail.com")
                .password("1231432").build());

        allUsers = this.repository.findAll();
        log.info("# of users: " + allUsers.size());
    }
}