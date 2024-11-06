package com.api.auth.rest.dto;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.api.auth.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @Schema(
        description = "Unique identifier of the Role.",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
        description = "Name of the Role.",
        example = "ADMIN"
    )
    @NotBlank(message = "Name is required.")
    private String name;

    public Role toModel() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Role.class);
    }
}
