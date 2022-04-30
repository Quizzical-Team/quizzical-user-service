package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.repositories.FriendRequestRepository;
import com.quizzical.userservice.repositories.PlayerRepository;
import com.quizzical.userservice.services.FriendRequestService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class PostgresFriendRequestService implements FriendRequestService {
    private final PlayerRepository playerRepository;
    private final FriendRequestRepository friendRequestRepository;

    public PostgresFriendRequestService(PlayerRepository playerRepository, FriendRequestRepository friendRequestRepository) {
        this.playerRepository = playerRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public Boolean sendFriendRequest(String fromPlayerName, String targetPlayerName) {
        if (fromPlayerName.equals(targetPlayerName))
            return false;

        String[] usernames = new String[] { fromPlayerName, targetPlayerName };
        List<Player> players = playerRepository.findAllByUsernameList(Arrays.asList(usernames));

        if (players.size() < 2)
            return false;

        if (doesFriendRequestExist(players.get(0), players.get(1)))
            return false;

        friendRequestRepository.save(new FriendRequest(players.get(0), players.get(1), false));
        return true;
    }

    @Override
    public Boolean respondToFriendRequest(String targetPlayerName, String fromPlayerName, Boolean response) {
        // check if same players
        // check if both players exist
        // check if players have friendship
        if ( targetPlayerName.equals( fromPlayerName))
            return false;

        String[] usernames = new String[]{ targetPlayerName,  fromPlayerName};
        List<Player> players = playerRepository.findAllByUsernameList(Arrays.asList(usernames));

        if (players.size() < 2)
            return false;

        FriendRequest fr = friendRequestRepository.findByReceiverAndSender(players.get(1), players.get(0))
                .orElse(null);
        if (fr == null)
            return false;

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

    @Override
    public Set<Player> getFriendsOfPlayer(String playerName, Integer pageNo, Integer pageSize) {
        return playerRepository.findByUsername(playerName).orElseThrow(UserNotFoundException::new).getFriends();
    }

    private Boolean doesFriendRequestExist(Player p1, Player p2) {
        return friendRequestRepository.existsByReceiverAndSender(p1, p2)
                || friendRequestRepository.existsByReceiverAndSender(p2, p1);
    }
}
