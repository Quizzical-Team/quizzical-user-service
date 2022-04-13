package com.quizzical.userservice.entities;

import com.quizzical.userservice.configuration.GamePropertiesConfig;
import lombok.*;

import javax.persistence.*;
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
        super(username, email, password);
    }

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
//     todo: set?
    private Set<Player> friends;

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> friendOf;

    // todo: match history

    public void changeMMRBy(Integer amount) {
        // clamp the MMR between max and min
        this.matchmakingRatio = Math.max(
                GamePropertiesConfig.minimumMatchmakingRatio,
                Math.min(GamePropertiesConfig.maximumMatchmakingRatio, matchmakingRatio + amount));
    }
}
