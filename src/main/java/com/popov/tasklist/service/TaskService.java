package com.popov.tasklist.service;

import com.popov.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {
    Task getByID(Long id);

    List<Task> getAllByUserID(Long userId);

    void assignToUserByID(Long taskId, Long userId);

    Task update(Task task);

    Task create(Task task, Long id);

    void delete(Long id);
}
