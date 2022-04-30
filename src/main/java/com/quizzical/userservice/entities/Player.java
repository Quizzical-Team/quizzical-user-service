package com.quizzical.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizzical.userservice.configuration.GamePropertiesConfig;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends User {
    @Column(name = "matchmaking_ratio", nullable = false)
    private Integer matchmakingRatio = GamePropertiesConfig.startingMatchmakingRatio;

    public Player(String username, String email, String password) {
        super(username, email, password, RoleType.ROLE_PLAYER);
    }

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private Set<Player> friends = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    @JsonIgnore
    private Set<Player> friendOf = new HashSet<>();

    // todo: match history

    public void changeMMRBy(Integer amount) {
        this.matchmakingRatio = Math.max( // clamp
                GamePropertiesConfig.minimumMatchmakingRatio,
                Math.min(GamePropertiesConfig.maximumMatchmakingRatio, matchmakingRatio + amount));
    }
}
