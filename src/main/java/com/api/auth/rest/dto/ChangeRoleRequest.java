package com.api.auth.rest.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleRequest {
    @Schema(
        description = "User ID", 
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @NotNull(message = "User ID is required.")
    private UUID userId;

    @Schema(
        description = "Role ID", 
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @NotNull(message = "Role ID is required.")
    private UUID roleId;
}
