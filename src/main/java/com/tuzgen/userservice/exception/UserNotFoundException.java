package com.tuzgen.userservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User " + id + " not found!");
    }
}
