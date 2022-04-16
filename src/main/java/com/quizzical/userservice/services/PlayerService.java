package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PlayerService extends AbstractUserService<Player> {
    Set<Player> searchPlayersStartingWith(String username);
    Set<Player> getPlayersByUsernames(Set<String> usernames);
    Boolean fluctuateMMR(Long id, Integer amount);
    Boolean fluctuateMMRBatch(Set<Long> ids, Collection<Integer> amounts);
    void banUser(Long id);
}
