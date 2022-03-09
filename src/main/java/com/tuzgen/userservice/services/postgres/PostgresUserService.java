package com.tuzgen.userservice.services.postgres;

import com.tuzgen.userservice.entity.User;
import com.tuzgen.userservice.repository.UserRepository;
import com.tuzgen.userservice.services.UserService;

import java.util.List;
import java.util.Optional;

public class PostgresUserService implements UserService {
    UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> getUsersPaginated(Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Boolean logInWithUsernameAndPassword(String userName, String password) {

        return null;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public List<User> addUsers(List<User> users) {
        return null;
    }

    @Override
    public void deleteUsers(List<Long> ids) {

    }
}
