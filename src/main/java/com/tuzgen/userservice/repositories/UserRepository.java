package com.tuzgen.userservice.repositories;

import com.tuzgen.userservice.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllById(Long id, Pageable pageable);
    List<User> findAllByUserName(String name, Pageable pageable);
    List<User> findAllByUserNameContains(String name, Pageable pageable);
}
