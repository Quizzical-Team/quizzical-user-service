package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Token;

public interface AuthenticationService {
    Token loginWithUsernamePassword(String username, String password);
    Boolean validateJwtToken(String jwt);
}
