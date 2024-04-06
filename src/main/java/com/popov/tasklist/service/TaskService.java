package com.popov.tasklist.service;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.domain.task.TaskImage;

import java.util.List;

public interface TaskService {
    Task getByID(Long id);

    List<Task> getAllByUserID(Long userId);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);
}
