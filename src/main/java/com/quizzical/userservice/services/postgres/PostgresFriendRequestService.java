package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.repositories.FriendRequestRepository;
import com.quizzical.userservice.repositories.PlayerRepository;
import com.quizzical.userservice.services.FriendRequestService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PostgresFriendRequestService implements FriendRequestService {
    private final PlayerRepository playerRepository;
    private final FriendRequestRepository friendRequestRepository;

    public PostgresFriendRequestService(PlayerRepository playerRepository, FriendRequestRepository friendRequestRepository) {
        this.playerRepository = playerRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public Boolean sendFriendRequest(Long fromPlayerId, Long targetPlayerId) {
        if (fromPlayerId.equals(targetPlayerId))
            return false;

        Long[] ids = new Long[] { fromPlayerId, targetPlayerId };
        List<Player> players = playerRepository.findAllById(Arrays.asList(ids));

        if (players.size() < 2)
            return false;

        if (doesFriendRequestExist(players.get(0), players.get(1)))
            return false;

        friendRequestRepository.save(new FriendRequest(players.get(0), players.get(1), false));
        return true;
    }

    @Override
    public Boolean respondToFriendRequest(Long targetPlayerId, Long fromPlayerId, Boolean response) {
        // check if same players
        // check if both players exist
        // check if players have friendship
        if (targetPlayerId.equals(fromPlayerId))
            return false;

        Long[] ids = new Long[]{targetPlayerId, fromPlayerId};
        List<Player> players = playerRepository.findAllById(Arrays.asList(ids));

        if (players.size() < 2)
            return false;

        if (doesFriendRequestExist(players.get(0), players.get(1)))
            return false;

        FriendRequest fr = friendRequestRepository.findByReceiverAndSender(targetPlayerId, fromPlayerId)
                .orElseThrow(() -> new RuntimeException("No friend request to respond"));
        if (response) {
            fr.setAccepted(true);
            friendRequestRepository.save(fr);

            players.get(0).getFriends().add(players.get(1));
            players.get(1).getFriends().add(players.get(0));
            playerRepository.saveAll(players);
        } else {
            friendRequestRepository.delete(fr);
        }
        return true;
    }

    private Boolean doesFriendRequestExist(Player p1, Player p2) {
        return friendRequestRepository.existsByReceiverAndSender(p1.getId(), p2.getId())
                || friendRequestRepository.existsByReceiverAndSender(p2.getId(), p1.getId());
    }
}
