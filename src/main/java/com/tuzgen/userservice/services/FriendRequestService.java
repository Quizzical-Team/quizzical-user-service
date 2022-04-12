package com.tuzgen.userservice.services;

public interface FriendRequestService {
    Boolean sendFriendRequest(Long fromPlayerId, Long targetPlayerId);
    Boolean respondToFriendRequest(Long targetPlayerId, Long fromPlayerId, Boolean response);
}
