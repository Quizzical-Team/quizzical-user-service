package com.tuzgen.userservice.services;

import com.tuzgen.userservice.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends AbstractUserService<User> {
    // safe ops
}
