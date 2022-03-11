package com.tuzgen.userservice.services.postgres;

import com.tuzgen.userservice.entities.User;
import com.tuzgen.userservice.exceptions.UserNotFoundException;
import com.tuzgen.userservice.repositories.UserRepository;
import com.tuzgen.userservice.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostgresUserService implements UserService {
    UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> getUsersPaginated(Integer pageNo, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(pageNo, pageSize)).toList();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean logInWithUsernameAndPassword(String userName, String password) {

        return null;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setUserName(user.getUserName());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> u = userRepository.findById(id);
        userRepository.deleteById(u.orElseThrow(() -> new UserNotFoundException(id)).getId());
    }

    @Override
    public List<User> addUsers(List<User> users) {
        return null;
    }

    @Override
    public void deleteUsers(List<Long> ids) {

    }
}
