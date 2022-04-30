package com.quizzical.userservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizzical.userservice.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private Boolean isBanned;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>() {{
            add(new SimpleGrantedAuthority(user.getRole().name()));
        }};

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getIsBanned(), authorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // todo: implement this
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
