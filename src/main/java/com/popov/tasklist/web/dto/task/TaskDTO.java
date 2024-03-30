package com.popov.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.validation.OnCreate;
import com.popov.tasklist.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    @NotNull(message = "Id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Title cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 50, message = "Title must be between 3 and 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "Description must be less than 255 characters", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;
}
