package com.tuzgen.userservice.dtos;

import com.tuzgen.userservice.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseEntityDto {
    // filtered: password, tokens, email address
    private final String userName;

    public UserDto(User entity) {
        super(entity);
        this.userName = entity.getUserName();
    }
}
