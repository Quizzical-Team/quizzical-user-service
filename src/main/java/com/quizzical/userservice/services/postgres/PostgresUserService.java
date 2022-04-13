package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.User;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.repositories.UserRepository;
import com.quizzical.userservice.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostgresUserService implements UserService {
    private final UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
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
    public void addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder(11).encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setUsername(user.getUsername());
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
        return userRepository.saveAll(users);
    }

    @Override
    public void deleteUsers(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }
}
