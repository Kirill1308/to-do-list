package com.popov.tasklist.repository;

import com.popov.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findByID(Long id);

    List<Task> findAllByUserID(Long userId);

    void assignToUserByID(Long taskId, Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);

}
