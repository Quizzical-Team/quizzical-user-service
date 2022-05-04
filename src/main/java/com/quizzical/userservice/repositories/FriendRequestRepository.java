package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findByReceiverAndSender(Player receiver, Player sender);
    Boolean existsByReceiverAndSender(Player receiver, Player sender);

    @Query("FROM FriendRequest f WHERE f.receiver.username=:playerName")
    Set<FriendRequest> findByReceiver(String playerName);
}
