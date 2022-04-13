package com.quizzical.userservice.controllers;

import com.quizzical.userservice.dtos.UserDto;
import com.quizzical.userservice.entities.User;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService repository) {
        this.userService = repository;
    }

    // not secure
    @GetMapping({"/page"})
    public List<User> showUsersPaginated(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return userService.getUsersPaginated(pageNo, pageSize);
    }

    // not secure
    @GetMapping("/{userId}")
    public User showUserInfo(@PathVariable("userId") Long userId) throws UserNotFoundException {
        return userService.getUser(userId);
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

    // secure -> admin or user privilege
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == principal")
    public User updateUser(@RequestBody User newUser, @PathVariable("userId") Long userId) {
        return userService.updateUser(userId, newUser);
    }

    // secure -> admin or user privilege
    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == principal")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
