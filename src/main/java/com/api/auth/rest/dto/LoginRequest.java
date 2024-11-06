package com.api.auth.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(
        description = "User email", 
        example = "john@gmail.com"
    )
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(
        description = "User password", 
        example = "password123"
    )
    @NotBlank(message = "Password is required")
    private String password;
}
