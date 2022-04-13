package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query(value = "FROM FriendRequest f WHERE f.receiver.id=:receiverId and f.sender.id=:senderId")
    Optional<FriendRequest> findByReceiverAndSender(Long receiverId, Long senderId);
    @Query(value = "SELECT CASE WHEN count(f) > 0 THEN true ELSE false END " +
            "FROM FriendRequest f WHERE f.receiver.id=:receiverId and f.sender.id=:senderId")
    Boolean existsByReceiverAndSender(Long receiverId, Long senderId);
}
