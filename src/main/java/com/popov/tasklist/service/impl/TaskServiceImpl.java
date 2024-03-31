package com.popov.tasklist.service.impl;

import com.popov.tasklist.domain.exception.ResourceNotFoundException;
import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.repository.TaskRepository;
import com.popov.tasklist.repository.UserRepository;
import com.popov.tasklist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public Task getByID(Long id) {
        return taskRepository.findByID(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserID(Long userId) {
        return taskRepository.findAllByUserID(userId);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        if (task == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.update(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        task.setStatus(Status.TODO);
        taskRepository.create(task);
        taskRepository.assignToUserByID(task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
