package br.com.gabrieudev.auth.infrastructrure.web.dtos;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.auth.domain.entities.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleDTO {
    @Schema(
        description = "Nome da Role",
        example = "ADMIN",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;

    @Schema(
        description = "Descrição da Role",
        example = "Role de Administrador",
        required = false
    )
    private String description;

    public static RoleDTO from(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role toDomainObj() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Role.class);
    }
}