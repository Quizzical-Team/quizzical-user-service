package com.tuzgen.userservice.repositories;

import com.tuzgen.userservice.entities.FriendRequest;
import com.tuzgen.userservice.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findByReceiverAndSender(Player receiver, Player sender);
    Boolean existsByReceiverAndSender(Player receiver, Player sender);
}
