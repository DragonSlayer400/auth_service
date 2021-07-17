package org.example.models.repos;

import org.example.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByLogin(String login);
}
