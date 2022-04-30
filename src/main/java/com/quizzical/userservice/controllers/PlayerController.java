package com.quizzical.userservice.controllers;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/v1/players")
@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerName}")
    @PreAuthorize("#playerName == principal.username")
    public Player read(@PathVariable("playerName") String playerName) {
        return playerService.getUser(playerName);
    }

    @GetMapping("/search")
    public Set<Player> searchByUsername(@RequestParam("searchParam") String searchParam) {
        return playerService.searchPlayersStartingWith(searchParam);
    }

    @PutMapping("/mmr")
    public ResponseEntity<?> updateMatchmakingRatiosOfPlayers(@RequestBody Map<String, Integer> updates) {
        if (playerService.fluctuateMMRBatch(updates.keySet(), updates.values()))
            return new ResponseEntity<>("Sucessfully updated mmr ratios", HttpStatus.OK);
        else
            return new ResponseEntity<>("Error updating mmr ratios", HttpStatus.BAD_REQUEST);
    }

    @GetMapping({"/page"})
    public List<Player> readPaginated(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return playerService.getUsersPaginated(pageNo, pageSize);
    }

    @GetMapping("/all")
    public List<Player> readAll() {
        return playerService.getAllUsers();
    }

    @PostMapping({""})
    public ResponseEntity<Player> create(@RequestBody Player player) {
        playerService.addUser(player);
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    @PutMapping("/{playerName}")
    @PreAuthorize("#playerName == principal.username")
    public Player update(@RequestBody Player newPlayer, @PathVariable("playerName") String playerName) {
        return playerService.updateUser(playerName, newPlayer);
    }

    @DeleteMapping("/{playerName}")
    @PreAuthorize("#playerName == principal")
    public void delete(@PathVariable("playerName") String playerName) {
        playerService.deleteUser(playerName);
    }
}
