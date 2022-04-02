package com.tuzgen.userservice.bootstrap;

import com.tuzgen.userservice.entities.User;
import com.tuzgen.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@ComponentScan("com.tuzgen.userservice.repositories")
@Profile("dev")
public class LoadDatabase {

    private final UserRepository repository;

    public LoadDatabase(UserRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        this.repository.saveAll(
                new ArrayList<>() {{
                    add(User.builder()
                            .username("oguztuzgen")
                            .email("oguztuzgen@gmail.com")
                            .password(new BCryptPasswordEncoder().encode("123123")).build()
                    );
                    add(User.builder()
                            .username("tuzgosh")
                            .email("tuzgosh@gmail.com")
                            .password(new BCryptPasswordEncoder().encode("123123")).build()
                    );
                    add(User.builder()
                            .username("torvalds")
                            .email("torvalds@gmail.com")
                            .password(new BCryptPasswordEncoder().encode("123123")).build()
                    );
                    add(User.builder()
                            .username("zuck")
                            .email("zuck@gmail.com")
                            .password(new BCryptPasswordEncoder().encode("123123")).build()
                    );
                }}
        );
        log.info("Finished database bootstrap...");
    }
}