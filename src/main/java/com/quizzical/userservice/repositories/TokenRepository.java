package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
