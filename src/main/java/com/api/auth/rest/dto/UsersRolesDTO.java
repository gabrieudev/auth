package com.api.auth.rest.dto;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.api.auth.model.UsersRoles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRolesDTO {
    @Schema(
        description = "Unique identifier of the UsersRoles.",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
        description = "User associated with the UsersRoles.",
        example = "{ id: 123e4567-e89b-12d3-a456-426614174000, firstName: John, lastName: Doe, email:}"
    )
    @NotNull(message = "User is required.")
    private UserDTO user;

    @Schema(
        description = "Role associated with the UsersRoles.",
        example = "{ id: 123e4567-e89b-12d3-a456-426614174000, name: ROLE_USER }"
    )
    @NotNull(message = "Role is required.")
    private RoleDTO role;

    public UsersRoles toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsersRoles.class);
    }
}
