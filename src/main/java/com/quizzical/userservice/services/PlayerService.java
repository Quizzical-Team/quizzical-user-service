package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PlayerService extends AbstractUserService<Player> {
    Set<Player> searchPlayersStartingWith(String username);

    List<Player> getPlayersByUsernames(Set<String> usernames);

    Boolean fluctuateMMR(String username, Integer amount);

    Boolean fluctuateMMRBatch(Set<String> usernames, Collection<Integer> amounts);

    void banUser(Long id);
}
