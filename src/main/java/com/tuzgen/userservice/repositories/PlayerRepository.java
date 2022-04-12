package com.tuzgen.userservice.repositories;

import com.tuzgen.userservice.entities.Player;

import java.util.List;

public interface PlayerRepository extends AbstractUserRepository<Player> {
    List<Player> findPlayersByUsernameIsStartingWith(String username);
}
