package com.tuzgen.userservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_banned")
    private Boolean isBanned = false;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    private Set<Token> tokens = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addUserToken(Token token) {
        tokens.add(token);
    }
}
