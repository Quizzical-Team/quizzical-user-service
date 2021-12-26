package com.tuzgen.userservice.repository;

import com.tuzgen.userservice.UserServiceApplication;
import com.tuzgen.userservice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private UserRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<User> allUsers = this.repository.findAll();
        log.info("# of users: " + allUsers.size());

        log.info("Saving new user...");
        this.repository.save(new User("Oguz"));
        this.repository.save(new User("Ahmet"));
        this.repository.save(new User("Mark"));
        this.repository.save(new User("Goksenin"));
        this.repository.save(new User("Murat"));
        this.repository.save(new User("Mehmet"));
        this.repository.save(new User("Emre"));
        this.repository.save(new User("Kim"));

        allUsers = this.repository.findAll();
        log.info("# of users: " + allUsers.size());
    }

//
//    @Bean
//    CommandLineRunner initDatabase(UserRepository repository) {
//        return args -> {
//            log.info("Preloading " + repository.save(new User("Oguz")));
//        };
//    }
}
