package com.popov.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.popov.tasklist.validation.OnCreate;
import com.popov.tasklist.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {
    @NotNull(message = "Id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Username cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 50, message = "Username must be between 3 and 50 characters", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password cannot be null", groups = {OnCreate.class})
    private String passwordConfirm;
}
