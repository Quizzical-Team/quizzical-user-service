package com.tuzgen.userservice.security;

import com.tuzgen.userservice.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Slf4j
public class UserPrincipal implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public UserPrincipal(User user,
                         Collection<? extends GrantedAuthority> authorities,
                         boolean accountNonExpired,
                         boolean accountNonLocked,
                         boolean credentialsNonExpired,
                         boolean enabled) {
        this.user = user;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this(user, authorities, true, true, true, true);
    }

    public UserPrincipal(User user) {
        this(user, new ArrayList<GrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority("User"))));
        log.debug(this.authorities.toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
