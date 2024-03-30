package com.popov.tasklist.web.controller;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.service.TaskService;
import com.popov.tasklist.validation.OnUpdate;
import com.popov.tasklist.web.dto.task.TaskDTO;
import com.popov.tasklist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    public TaskDTO update(@Validated(OnUpdate.class) @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable Long id) {
        Task task = taskService.getByID(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }
}
