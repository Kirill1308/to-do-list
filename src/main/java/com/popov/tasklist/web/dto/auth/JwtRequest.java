package com.popov.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request object for JWT authentication")
public class JwtRequest {

    @Schema(description = "email", example = "johndoe@gmail.com")
    @NotNull(message = "Username cannot be null")
    private String username;

    @Schema(description = "password", example = "12345")
    @NotNull(message = "Password cannot be null")
    private String password;
}
