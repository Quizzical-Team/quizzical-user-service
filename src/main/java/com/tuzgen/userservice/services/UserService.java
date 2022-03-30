package com.tuzgen.userservice.services;

import com.tuzgen.userservice.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    // safe ops
    User getUser(Long id);
    User getUser(String username);
    List<User> getUsersPaginated(Integer pageNo, Integer pageSize);
    List<User> getAllUsers();

    Boolean logInWithUsernameAndPassword(String userName, String password);

    // non-safe ops
    void addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    // batch jobs
    List<User> addUsers(List<User> users);
    void deleteUsers(List<Long> ids);
}
