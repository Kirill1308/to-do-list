package com.popov.tasklist.domain.user;

import com.popov.tasklist.domain.task.Task;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;
    private List<Task> tasks;
}
