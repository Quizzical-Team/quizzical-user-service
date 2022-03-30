package com.tuzgen.userservice.services;

import com.tuzgen.userservice.entities.Player;

public interface PlayerService extends UserService {
    Boolean sendFriendRequest(Player targetPlayer);
//    Boolean respondFriendRequest(Player targetPlayer, Boolean responseToFriendRequest);
    Boolean removeFriend(Player targetPlayer);
    void fluctuateMMR(Integer amount);
    void banUser(Long id);
}
