package com.tuzgen.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "players")
//@PrimaryKeyJoinColumn(name = "user")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends User {
    @Column(name = "matchmakingRatio", nullable = false)
    private Integer matchmakingRatio = 200;

    @Column(name = "isBanned", nullable = true)
    private Boolean isBanned = false;

    // todo: match history

}
