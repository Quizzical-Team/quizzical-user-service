package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.List;
import java.util.Set;

public interface PlayerService extends AbstractUserService<Player> {
    Set<Player> searchPlayersStartingWith(String username);
    Set<Player> getPlayersByUsernames(Set<String> usernames);
    void fluctuateMMR(Long id, Integer amount);
    void fluctuateMMRBatch(List<Long> ids, List<Integer> amounts);
    void banUser(Long id);
}
