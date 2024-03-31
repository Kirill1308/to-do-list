package com.popov.tasklist.web.controller;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.domain.user.User;
import com.popov.tasklist.service.TaskService;
import com.popov.tasklist.service.UserService;
import com.popov.tasklist.validation.OnUpdate;
import com.popov.tasklist.web.dto.task.TaskDTO;
import com.popov.tasklist.web.dto.user.UserDTO;
import com.popov.tasklist.web.mappers.TaskMapper;
import com.popov.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    public UserDTO update(@Validated(OnUpdate.class) @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDTO> getAllTasksByUserID(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserID(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    public TaskDTO createTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}
