package com.quizzical.userservice.services;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.entities.User;

import java.util.List;
import java.util.Set;

public interface AbstractUserService<T extends User> {
    T getUser(String username);
    List<T> getUsersPaginated(Integer pageNo, Integer pageSize);
    List<T> getAllUsers();

    // non-safe ops
    void addUser(T user);
    T updateUser(String id, Player user);
    void deleteUser(String id);

    Set<Player> getTop10ByMmr();
}
