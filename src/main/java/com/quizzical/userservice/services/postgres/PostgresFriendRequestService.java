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
    public Boolean sendFriendRequest(String sender, String receiver) {
        if (sender.equals(receiver))
            return false;

        String[] usernames = new String[] { sender, receiver };
        List<Player> players = playerRepository.findAllByUsernameList(Arrays.asList(sender, receiver));

        if (players.size() < 2)
            return false;

        if (doesFriendRequestExist(players.get(0), players.get(1)))
            return false;

        friendRequestRepository.save(new FriendRequest(players.get(0), players.get(1), null));
        return true;
    }

    @Override
    public Boolean respondToFriendRequest(String receiver, String sender, Boolean response) {
        // check if same players
        // check if both players exist
        // check if players have friendship
        if (receiver.equals(sender)) {
            System.out.println("ZORT 1");
            return false;
        }

        Player pSender = playerRepository.findByUsername(sender).orElse(null);
        Player pReceiver = playerRepository.findByUsername(receiver).orElse(null);

        if (pSender == null || pReceiver == null) {
            System.out.println("ZORT 2");
            return false;
        }

        FriendRequest fr = friendRequestRepository.findByReceiverAndSender(pReceiver, pSender)
                .orElse(null);
        if (fr == null) {
            System.out.println(receiver + pReceiver.getUsername() + " " + sender + " ZORT 3");
            return false;
        }

        if (response) {

            pSender.getFriends().add(pReceiver);
            pReceiver.getFriends().add(pSender);
            playerRepository.saveAll(Arrays.asList(pReceiver, pSender));
            friendRequestRepository.delete(fr);

        } else {
            friendRequestRepository.delete(fr);
        }
        return true;
    }

    @Override
    public Set<Player> getFriendsOfPlayer(String playerName, Integer pageNo, Integer pageSize) {
        return playerRepository.findByUsername(playerName).orElseThrow(UserNotFoundException::new).getFriends();
    }

    @Override
    public Set<FriendRequest> getFriendRequestsOfPlayer(String playerName, Integer pageNo, Integer pageSize) {
        return friendRequestRepository.findByReceiver(playerName);
    }

    @Override
    public Boolean removeExistingFriend(String playerName, String friendToRemove) {
        List<Player> players = playerRepository.findAllByUsernameList(Arrays.asList(playerName, friendToRemove));
        if (players.get(0).getFriends().contains(players.get(1))) {
            players.get(0).getFriends().remove(players.get(1));
            players.get(1).getFriends().remove(players.get(0));

            players.get(0).getFriendOf().remove(players.get(1));
            players.get(1).getFriendOf().remove(players.get(0));

            playerRepository.saveAll(players);
            return true;
        } else {
            return false;
        }
    }

    private Boolean doesFriendRequestExist(Player p1, Player p2) {
        return friendRequestRepository.existsByReceiverAndSender(p1, p2)
                || friendRequestRepository.existsByReceiverAndSender(p2, p1);
    }
}
