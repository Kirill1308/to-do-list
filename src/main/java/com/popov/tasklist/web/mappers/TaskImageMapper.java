package com.popov.tasklist.web.mappers;

import com.popov.tasklist.domain.task.TaskImage;
import com.popov.tasklist.web.dto.task.TaskImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDTO> {
}
