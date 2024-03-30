package com.popov.tasklist.web.mappers;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.web.dto.task.TaskDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);

    List<TaskDTO> toDto(List<Task> tasks);

    Task toEntity(TaskDTO taskDto);
}
