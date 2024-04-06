package com.popov.tasklist.service.impl;

import com.popov.tasklist.domain.exception.ResourceNotFoundException;
import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.domain.task.TaskImage;
import com.popov.tasklist.domain.user.User;
import com.popov.tasklist.repository.TaskRepository;
import com.popov.tasklist.service.ImageService;
import com.popov.tasklist.service.TaskService;
import com.popov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "TaskService::getByID", key = "#id")
    public Task getByID(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserID(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getByID", key = "#task.id")
    public Task update(Task task) {
        if (task == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(value = "TaskService::getByID", key = "#task.id")
    public Task create(Task task, Long userId) {
        User user = userService.getById(userId);
        task.setStatus(Status.TODO);
        user.getTasks().add(task);
        userService.update(user);
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getByID", key = "#id")
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getByID", key = "#id")
    public void uploadImage(Long id, TaskImage image) {
        Task task = getByID(id);
        String fileName = imageService.uploadImage(image);
        task.getImages().add(fileName);
        taskRepository.save(task);
    }
}
