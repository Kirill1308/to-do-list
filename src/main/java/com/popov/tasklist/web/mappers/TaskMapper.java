package com.popov.tasklist.web.mappers;

import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.web.dto.task.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDTO> {
}
