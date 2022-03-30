package com.tuzgen.userservice.entities;

import lombok.*;
import org.hibernate.Hibernate;

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
    // ~ JPA items
    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_banned")
    private Boolean isBanned = false;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    private Set<Token> tokens = new HashSet<>();

    public void addUserToken(Token token) {
        tokens.add(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return super.getId() != null && Objects.equals(super.getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
