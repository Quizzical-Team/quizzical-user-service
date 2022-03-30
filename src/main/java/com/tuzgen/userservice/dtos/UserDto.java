package com.tuzgen.userservice.dtos;

import com.tuzgen.userservice.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String email;
//    private String password;

    public UserDto(User entity) {
        this.username = entity.getUsername();
        this.email = entity.getEmail();
//        this.password = entity.getPassword();
    }

}
