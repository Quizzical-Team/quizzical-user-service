package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.Player;

import java.util.List;

public interface PlayerRepository extends AbstractUserRepository<Player> {
    List<Player> findPlayersByUsernameIsStartingWith(String username);
}
