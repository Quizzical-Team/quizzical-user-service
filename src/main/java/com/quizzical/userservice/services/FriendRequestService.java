package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;

import java.util.Set;

public interface FriendRequestService {
    Boolean sendFriendRequest(String sender, String receiver);
    Boolean respondToFriendRequest(String targetPlayerId, String fromPlayerId, Boolean response);

    Set<Player> getFriendsOfPlayer(String playerName, Integer pageNo, Integer pageSize);

    Set<FriendRequest> getFriendRequestsOfPlayer(String playerName, Integer pageNo, Integer pageSize);
}
