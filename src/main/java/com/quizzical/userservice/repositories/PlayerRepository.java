package com.quizzical.userservice.repositories;

import com.quizzical.userservice.entities.Player;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PlayerRepository extends AbstractUserRepository<Player> {
    Set<Player> findPlayersByUsernameIsStartingWith(String username);

    // todo ?
    // search players by like query
//    @Query("FROM Player p WHERE p.username=:username and p in p.friends")
//    Set<Player>  findFriendsOfPlayerByLikeUsername(Long userId, String username);

    @Query("FROM Player p WHERE p.username in :usernames")
    List<Player> findAllByUsernameList(List<String> usernames);

    @Query("FROM Player p WHERE p.username in :usernames")
    List<Player> findAllByUsernameSet(Set<String> usernames);

    void deleteByUsername(String username);

    @Query(value = "SELECT * FROM PLAYERS ORDER BY matchmaking_ratio DESC LIMIT 10", nativeQuery = true)
    Set<Player> getTopPlayersByMmr();
}
