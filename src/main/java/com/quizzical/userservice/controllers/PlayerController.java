package com.quizzical.userservice.controllers;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RequestMapping("/api/v1/players")
@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    public Player showPlayerInfo(@PathVariable("playerId") Long playerId) {
        return playerService.getUser(playerId);
    }

    @GetMapping("/search")
    public Set<Player> searchPlayerByUsername(@RequestParam("searchParam") String searchParam) {
        return playerService.searchPlayersStartingWith(searchParam);
    }

    @PutMapping("/mmr")
    public ResponseEntity updateMatchmakingRatiosOfPlayers(@RequestBody Map<Long, Integer> updates) {
        if (playerService.fluctuateMMRBatch(updates.keySet(), updates.values()))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
