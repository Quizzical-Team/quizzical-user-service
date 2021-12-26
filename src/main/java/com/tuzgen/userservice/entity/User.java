package com.tuzgen.userservice.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public boolean equals(Object u) {
        if (this == u)
            return true;
        if (!(u instanceof User))
            return false;
        return this.id.equals(((User) u).id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", name='" + this.name + "'}";
    }
}
