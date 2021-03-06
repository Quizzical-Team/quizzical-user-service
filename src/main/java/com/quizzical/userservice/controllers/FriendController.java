package com.quizzical.userservice.controllers;

import com.quizzical.userservice.entities.FriendRequest;
import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.services.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/players/friend")

public class FriendController {
    private final FriendRequestService friendRequestService;

    public FriendController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PutMapping("/send")
    @PreAuthorize("#sender == principal.username")
    public ResponseEntity<?> sendFriendRequest(@RequestParam("receiver") String receiver, @RequestParam("sender") String sender) {
        if (friendRequestService.sendFriendRequest(receiver, sender)) {
            return new ResponseEntity("Friend request sent", HttpStatus.OK);
        } else {
            return new ResponseEntity("Friend request cannot be sent", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/respond")
    @PreAuthorize("#receiver == principal.username")
    public ResponseEntity<?> respondToFriendRequest(@RequestParam("receiver") String receiver, @RequestParam("sender") String sender,
                                                 @RequestParam Boolean response) {
        if (friendRequestService.respondToFriendRequest(receiver, sender, response)) {
            return new ResponseEntity("Friend request accepted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity("Friend request could not be accepted!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{playerName}")
    @PreAuthorize("#playerName == principal.username")
    public Set<Player> getFriendsOfPlayerPaginated(@PathVariable("playerName") String playerName,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return friendRequestService.getFriendsOfPlayer(playerName, pageNo, pageSize);
    }

    @DeleteMapping("/{playerName}")
    @PreAuthorize("#playerName == principal.username")
    public ResponseEntity<?> deleteFriendship(@PathVariable String playerName, @RequestParam String target) {
        if (friendRequestService.removeExistingFriend(playerName, target)) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/requests/{playerName}")
    @PreAuthorize("#playerName == principal.username")
    public Set<FriendRequest> getFriendRequestsOfPlayerPaginated(
            @PathVariable("playerName") String playerName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return friendRequestService.getFriendRequestsOfPlayer(playerName, pageNo, pageSize);
    }
}
