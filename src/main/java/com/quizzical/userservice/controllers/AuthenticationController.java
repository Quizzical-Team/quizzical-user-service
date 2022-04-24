package com.quizzical.userservice.controllers;

import com.quizzical.userservice.dtos.CredentialsDto;
import com.quizzical.userservice.entities.Token;
import com.quizzical.userservice.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInWithUsernamePassword(@RequestBody CredentialsDto credentialsDto) {
        Token t = authenticationService.loginWithUsernamePassword(credentialsDto);
        if (t == null)
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}
