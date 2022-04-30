package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.Set;

public interface FriendRequestService {
    Boolean sendFriendRequest(String fromPlayerId, String targetPlayerId);
    Boolean respondToFriendRequest(String targetPlayerId, String fromPlayerId, Boolean response);

    Set<Player> getFriendsOfPlayer(String playerName, Integer pageNo, Integer pageSize);
}
