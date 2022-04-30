package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.entities.Token;
import com.quizzical.userservice.repositories.PlayerRepository;
import com.quizzical.userservice.repositories.TokenRepository;
import com.quizzical.userservice.security.JwtTokenUtil;
import com.quizzical.userservice.services.AuthenticationService;
import io.jsonwebtoken.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PostgresAuthenticationService implements AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public PostgresAuthenticationService(JwtTokenUtil jwtTokenUtil, PlayerRepository playerRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token loginWithUsernamePassword(String username, String password) {
        Player p = playerRepository.findByUsername(username).orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(password, p.getPassword())) {
            return null;
        }
        Token t = new Token(jwtTokenUtil.generateToken(username));

        tokenRepository.save(t);
        p.addUserToken(t);
        playerRepository.save(p);

        return t;
    }

    @Override
    public Boolean validateJwtToken(String jwt) {
        return jwtTokenUtil.validateToken(jwt);
    }
}
