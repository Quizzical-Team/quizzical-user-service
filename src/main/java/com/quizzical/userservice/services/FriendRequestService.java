package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;

import java.util.Set;

public interface FriendRequestService {
    Boolean sendFriendRequest(Long fromPlayerId, Long targetPlayerId);
    Boolean respondToFriendRequest(Long targetPlayerId, Long fromPlayerId, Boolean response);

    Set<Player> getFriendsOfPlayer(Long playerId, Integer pageNo, Integer pageSize);
}
