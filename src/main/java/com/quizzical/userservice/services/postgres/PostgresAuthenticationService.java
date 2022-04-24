package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.dtos.CredentialsDto;
import com.quizzical.userservice.entities.Token;
import com.quizzical.userservice.entities.User;
import com.quizzical.userservice.repositories.TokenRepository;
import com.quizzical.userservice.repositories.UserRepository;
import com.quizzical.userservice.security.JwtTokenUtil;
import com.quizzical.userservice.services.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PostgresAuthenticationService implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public PostgresAuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Token loginWithUsernamePassword(CredentialsDto credentialsDto) {

        User u = userRepository.findByUsername(credentialsDto.getUsername()).orElse(null);
        if (u == null)
            return null;

        if (!passwordEncoder.matches(credentialsDto.getPassword(), u.getPassword()))
            return null;


        Token token = Token.builder().tokenValue(
                jwtTokenUtil.generateToken(credentialsDto)
        ).build();

        u.addUserToken(token);
        tokenRepository.save(token);
        userRepository.save(u);

        return token;
    }


}
