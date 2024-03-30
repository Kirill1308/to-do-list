package com.popov.tasklist.repository.impl;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public Optional<Task> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAllByUserID(Long userId) {
        return null;
    }

    @Override
    public void assignToUserByID(Long taskId, Long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }
}
