package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.entities.User;

import java.util.List;

public interface AbstractUserService<T extends User> {
    T getUser(Long id);
    T getUser(String username);
    List<T> getUsersPaginated(Integer pageNo, Integer pageSize);
    List<T> getAllUsers();

    // non-safe ops
    void addUser(T user);
    T updateUser(String id, Player user);
    void deleteUser(String id);

    // batch jobs
    List<T> addUsers(List<T> users);
    void deleteUsers(List<Long> ids);
}
