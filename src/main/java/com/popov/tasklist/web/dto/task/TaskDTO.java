package com.popov.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.validation.OnCreate;
import com.popov.tasklist.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Task DTO")
public class TaskDTO {
    @Schema(description = "Task ID", example = "1")
    @NotNull(message = "Id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Task title", example = "Task 1")
    @NotNull(message = "Title cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 50, message = "Title must be between 3 and 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Schema(description = "Task description", example = "Description 1")
    @Length(max = 255, message = "Description must be less than 255 characters", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @Schema(description = "Task status", example = "IN_PROGRESS")
    private Status status;

    @Schema(description = "Task creation date", example = "2021-12-31 23:59")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;
}
