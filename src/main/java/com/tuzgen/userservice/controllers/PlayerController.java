package com.tuzgen.userservice.controllers;

import com.tuzgen.userservice.entities.Player;
import com.tuzgen.userservice.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Player> searchPlayerByUsername(@RequestParam("searchParam") String searchParam) {
        return playerService.searchPlayersStartingWith(searchParam);
    }

}
