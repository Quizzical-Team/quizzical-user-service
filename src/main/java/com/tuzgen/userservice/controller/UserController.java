package com.tuzgen.userservice.controller;

import com.tuzgen.userservice.entity.User;
import com.tuzgen.userservice.exception.UserNotFoundException;
import com.tuzgen.userservice.repository.LoadDatabase;
import com.tuzgen.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private final UserRepository userRepository;
    public UserController(UserRepository repository) {
        this.userRepository = repository;
    }

    // not secure
    @GetMapping("/users")
    public List<User> showUsersPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return userRepository.findAll(PageRequest.of(page, size)).toList();
    }

    // not secure
    @GetMapping("/users/all")
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    // secure -> admin privilege
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        userRepository.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // not secure
    @GetMapping("/user")
    public User showUserInfo(@RequestParam Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // secure -> admin or user privilege
    @PutMapping("/user")
    public User updateUser(@RequestBody User newUser, @RequestParam Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // secure -> admin or user privilege
    @DeleteMapping("/user")
    public ResponseEntity<User> deleteUser(@RequestParam Long id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(u.get(), HttpStatus.OK);
        }
        else
            throw new UserNotFoundException(id);
    }
}
