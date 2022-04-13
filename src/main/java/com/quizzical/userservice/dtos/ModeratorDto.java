package com.quizzical.userservice.dtos;

import com.quizzical.userservice.entities.Moderator;
import com.quizzical.userservice.entities.ModeratorPermission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeratorDto extends UserDto {
    private final String phoneNumber;
    private final ModeratorPermission moderatorPermission;

    public ModeratorDto(Moderator entity) {
        super(entity);
        this.phoneNumber = entity.getPhoneNumber();
        this.moderatorPermission = entity.getPermissionLevel();
    }
}