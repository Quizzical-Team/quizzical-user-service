package com.tuzgen.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class UserNotFoundHandler {

    @ExceptionHandler(value = { UserNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public UserNotFoundDTO handleUserNotFound(UserNotFoundException ex) {
        return new UserNotFoundDTO(ex.getMessage());
    }

    private class UserNotFoundDTO {
        private final String message;
        private final String timestamp;

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }
        public UserNotFoundDTO(String m) {
            this.message = m;
            timestamp = new Timestamp(System.currentTimeMillis()).toString();
        }
    }
}
