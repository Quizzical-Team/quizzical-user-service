package com.tuzgen.userservice.repositories;

import com.tuzgen.userservice.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllById(Long id, Pageable pageable);
    Optional<User> findByUsername(String name);
    List<User> findAllByUsername(String name, Pageable pageable);
    List<User> findAllByUsernameContains(String name, Pageable pageable);
}
