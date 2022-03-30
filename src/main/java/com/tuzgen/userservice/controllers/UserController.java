package com.tuzgen.userservice.controllers;

import com.tuzgen.userservice.dtos.UserDto;
import com.tuzgen.userservice.entities.User;
import com.tuzgen.userservice.exceptions.UserNotFoundException;
import com.tuzgen.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService repository) {
        this.userService = repository;
    }

    // not secure
    @GetMapping({""})
    public List<User> showUsersPaginated(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return userService.getUsersPaginated(pageNo, pageSize);
    }

    // not secure
    @GetMapping("/all")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    // secure -> admin privilege
    @PostMapping({""})
    public ResponseEntity<UserDto> createNewUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
    }

    // not secure
    @GetMapping("/{userId}")
    public User showUserInfo(@PathVariable("userId") Long userId) throws UserNotFoundException {
        return userService.getUser(userId);
    }

    // secure -> admin or user privilege
    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        return userService.updateUser(userId, newUser);
    }

    // secure -> admin or user privilege
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
