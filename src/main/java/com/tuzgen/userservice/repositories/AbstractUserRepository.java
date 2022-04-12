package com.tuzgen.userservice.repositories;

import com.tuzgen.userservice.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractUserRepository<T extends User> extends JpaRepository<T, Long> {

    // define user actions here and they will be inherited in child classes such as
    // player and moderator
    List<T> findAllById(Long id, Pageable pageable);
    Optional<T> findByUsername(String name);
    List<T> findAllByUsername(String name, Pageable pageable);
    List<T> findAllByUsernameContains(String name, Pageable pageable);

}
