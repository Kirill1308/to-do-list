package com.popov.tasklist.repository;

import com.popov.tasklist.domain.user.Role;
import com.popov.tasklist.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<User> findByID(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void delete(Long id);

    void insertUserRole(@Param("userId") Long userId, @Param("role") Role role);

    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
