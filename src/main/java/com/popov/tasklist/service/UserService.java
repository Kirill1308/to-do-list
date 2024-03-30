package com.popov.tasklist.service;

import com.popov.tasklist.domain.user.User;

public interface UserService {
    User getByID(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);
}
