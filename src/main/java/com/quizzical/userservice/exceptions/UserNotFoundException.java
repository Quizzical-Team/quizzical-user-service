package com.quizzical.userservice.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id: " + id + " not found!");
    }

    public UserNotFoundException(String userName) {
        super("User with username: " + userName + " not found!");
    }

    public UserNotFoundException() {
        super("User not found!");
    }
}
