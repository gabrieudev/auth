package com.api.auth.rest.dto;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.api.auth.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Schema(
        description = "Unique identifier of the User.",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
        description = "First name of the User.",
        example = "John"
    )
    @NotBlank(message = "First name is required.")
    private String firstName;

    @Schema(
        description = "Last name of the User.",
        example = "Doe"
    )
    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Schema(
        description = "Email of the User.",
        example = "john@gmail.com"
    )
    @NotBlank(message = "Email is required.")
    private String email;

    @Schema(
        description = "Password of the User.",
        example = "password123"
    )
    @NotBlank(message = "Password is required.")
    private String password;

    public User toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, User.class);
    }
}
