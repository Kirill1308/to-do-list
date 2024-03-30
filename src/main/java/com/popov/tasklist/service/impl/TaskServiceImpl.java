package com.popov.tasklist.service.impl;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Task getByID(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserID(Long userId) {
        return null;
    }

    @Override
    public void assignToUserByID(Long taskId, Long userId) {

    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
