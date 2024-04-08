package com.popov.tasklist.service.impl;

import com.popov.tasklist.domain.exception.ResourceNotFoundException;
import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.domain.task.TaskImage;
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

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    @Cacheable(value = "TaskService::getByID", key = "#id")
    public Task getByID(final Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    public List<Task> getAllByUserID(final Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findAllSoonTasks(Timestamp.valueOf(now), Timestamp.valueOf(now.plus(duration)));
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getByID", key = "#task.id")
    public Task update(final Task task) {
        if (task == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(
            value = "TaskService::getById",
            condition = "#task.id!=null",
            key = "#task.id"
    )
    public Task create(
            final Task task,
            final Long userId
    ) {
//        if (task.getStatus() != null) {
        task.setStatus(Status.TODO);
//        }
        taskRepository.save(task);
        taskRepository.assignTask(userId, task.getId());
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getByID", key = "#id")
    public void delete(final Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getByID", key = "#id")
    public void uploadImage(final Long id, final TaskImage image) {
        Task task = getByID(id);
        String fileName = imageService.uploadImage(image);
        task.getImages().add(fileName);
        taskRepository.save(task);
    }
}
