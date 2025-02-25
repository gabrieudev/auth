package br.com.gabrieudev.auth.adapters.input.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.role.CreateRoleDTO;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.role.RoleDTO;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.role.UpdateRoleDTO;
import br.com.gabrieudev.auth.application.ports.input.RoleInputPort;
import br.com.gabrieudev.auth.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/roles")
public class RoleController {
    private final RoleInputPort roleInputPort;

    public RoleController(RoleInputPort roleInputPort) {
        this.roleInputPort = roleInputPort;
    }

    @Operation(
        summary = "Criar um papel",
        description = "Endpoint para criação de um papel.",
        tags = { "Role" },
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Papel criado com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Void.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Requisição inválida.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Papel já criado.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            )
        }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponseDTO<RoleDTO>> create(
        @Valid
        @RequestBody
        CreateRoleDTO createRoleDTO
    ) {
        Role role = roleInputPort.create(createRoleDTO.toDomainObj());

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(RoleDTO.from(role)));
    }

    @Operation(
        summary = "Atualizar um papel",
        description = "Endpoint para atualização de um papel.",
        tags = { "Role" },
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Papel atualizado com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Void.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Papel não encontrado.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Papel ja cadastrado.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            )
        }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponseDTO<RoleDTO>> update(
        @Valid
        @RequestBody
        UpdateRoleDTO updateRoleDTO    
    ) {
        Role role = roleInputPort.update(updateRoleDTO.toDomainObj());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(RoleDTO.from(role)));
    }

    @Operation(
        summary = "Listar papeis",
        description = "Endpoint para listagem de papeis.",
        tags = { "Role" },
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Papeis listados com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Void.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            )
        }
    )
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RoleDTO>>> findAll(
        @Schema(
            name = "userId",
            description = "ID do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @RequestParam(required = false) UUID userId,

        @Schema(
            name = "name",
            description = "Nome do papel",
            example = "ADMIN"
        )
        @RequestParam(required = false) String name
    ) {
        List<RoleDTO> roles = roleInputPort.findAll(userId, name)
            .stream()
            .map(RoleDTO::from)
            .toList();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(roles));
    }

    @Operation(
        summary = "Obter um papel",
        description = "Endpoint para obter um papel.",
        tags = { "Role" },
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Papel obtido com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Void.class
                    )   
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Papel não encontrado.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            )
        }
    )
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RoleDTO>> findById(
        @Schema(
            description = "ID do papel",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        Role role = roleInputPort.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(RoleDTO.from(role)));
    }

    @Operation(
        summary = "Excluir um papel",
        description = "Endpoint para exclusão de um papel.",
        tags = { "Role" },
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Papel excluido com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token inválido.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = Void.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Papel não encontrado.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Papel possui usuários associados.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor.",
                content = @Content(
                    schema = @Schema(implementation = ApiResponseDTO.class)
                )
            )
        }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> delete(
        @Schema(
            description = "ID do papel",
            example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @PathVariable UUID id
    ) {
        roleInputPort.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponseDTO.noContent("Papel excluido com sucesso."));
    }
}
