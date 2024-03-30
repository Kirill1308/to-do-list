package com.popov.tasklist.repository;

import com.popov.tasklist.domain.user.Role;
import com.popov.tasklist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByID(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void delete(Long id);

    void insertUserRole(Long userId, Role role);

    boolean isTaskOwner(Long userId, Long taskId);
}
