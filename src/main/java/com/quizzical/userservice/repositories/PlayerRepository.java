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
    Set<Player> findAllByUsernameList(Set<String> usernames);
}
