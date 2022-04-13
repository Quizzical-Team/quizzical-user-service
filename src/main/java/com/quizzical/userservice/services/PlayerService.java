package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.List;

public interface PlayerService extends AbstractUserService<Player> {
    List<Player> searchPlayersStartingWith(String username);
    void fluctuateMMR(Long id, Integer amount);
    void fluctuateMMRBatch(List<Long> ids, List<Integer> amounts);
    void banUser(Long id);
}
