package com.tuzgen.userservice.services;

import com.tuzgen.userservice.entities.User;

import java.util.List;

public interface UserService {
    // safe ops
    User getUser(Long id);
    List<User> getUsersPaginated(Integer pageNo, Integer pageSize);
    List<User> getAllUsers();

    Boolean logInWithUsernameAndPassword(String userName, String password);

    // non-safe ops
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    // batch jobs
    List<User> addUsers(List<User> users);
    void deleteUsers(List<Long> ids);
}
