package com.quizzical.userservice.controllers;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.services.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/player/friend")

public class FriendController {
    private final FriendRequestService friendRequestService;

    public FriendController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PutMapping("/send")
    @PreAuthorize("#senderId == principal")
    public ResponseEntity sendFriendRequest(@RequestParam("receiverId") Long receiverId, @RequestParam("senderId") Long senderId) {
        if (friendRequestService.sendFriendRequest(senderId, receiverId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/respond")
    @PreAuthorize("#receiverId == principal")
    public ResponseEntity respondToFriendRequest(@RequestParam("receiverId") Long receiverId, @RequestParam("senderId") Long senderId, @RequestParam Boolean response) {
        if (friendRequestService.respondToFriendRequest(receiverId, senderId, response)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{playerId}")
    @PreAuthorize("#playerId == principal")
    public Set<Player> getFriendsOfPlayerPaginated(@PathVariable("playerId") Long playerId,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return friendRequestService.getFriendsOfPlayer(playerId, pageNo, pageSize);
    }
}
