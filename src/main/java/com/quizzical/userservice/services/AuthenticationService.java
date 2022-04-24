package com.quizzical.userservice.services;

import com.quizzical.userservice.dtos.CredentialsDto;
import com.quizzical.userservice.entities.Token;

public interface AuthenticationService {
    Token loginWithUsernamePassword(CredentialsDto credentialsDto);
}
