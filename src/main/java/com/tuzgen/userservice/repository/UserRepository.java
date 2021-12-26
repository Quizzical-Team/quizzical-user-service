package com.tuzgen.userservice.repository;

import com.tuzgen.userservice.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllById(Long id, Pageable pageable);
    List<User> findAllByName(String name, Pageable pageable);
}
