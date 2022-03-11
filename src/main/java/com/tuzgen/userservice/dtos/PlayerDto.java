package com.tuzgen.userservice.dtos;

import com.tuzgen.userservice.entities.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto extends UserDto {
    private final Integer matchmakingRatio;
    private final Boolean isBanned;

    public PlayerDto(Player entity) {
        super(entity);
        this.matchmakingRatio = entity.getMatchmakingRatio();
        this.isBanned = entity.getIsBanned();
    }
}
