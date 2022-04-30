package com.quizzical.userservice.controllers;

import com.quizzical.userservice.dtos.CredentialsDto;
import com.quizzical.userservice.entities.Token;
import com.quizzical.userservice.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInWithUsernamePassword(@RequestBody CredentialsDto credentialsDto) {
        Token t = authenticationService.loginWithUsernamePassword(credentialsDto.getUsername(), credentialsDto.getPassword());
        if (t == null)
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping("/validateToken/{token}")
    public ResponseEntity<?> validateToken(@PathVariable("token") String token) {
        return authenticationService.validateJwtToken(token) ? ResponseEntity.ok("Success") : ResponseEntity.ok("Failure");
    }
}
