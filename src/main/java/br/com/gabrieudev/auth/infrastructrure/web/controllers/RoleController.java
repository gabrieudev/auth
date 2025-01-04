package br.com.gabrieudev.auth.infrastructrure.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.auth.application.exceptions.StandardException;
import br.com.gabrieudev.auth.application.usecases.RoleInteractor;
import br.com.gabrieudev.auth.infrastructrure.web.dtos.CreateRoleDTO;
import br.com.gabrieudev.auth.infrastructrure.web.dtos.RoleDTO;
import br.com.gabrieudev.auth.infrastructrure.web.dtos.UpdateRoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final RoleInteractor roleInteractor;
    
    public RoleController(RoleInteractor roleInteractor) {
        this.roleInteractor = roleInteractor;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Criar role",
        description = "Cria uma role de acordo com o corpo da requisição",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Role criada"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<RoleDTO> save(
        @Valid
        @RequestBody
        CreateRoleDTO createRoleDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleDTO.from(roleInteractor.save(createRoleDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Atualizar role",
        description = "Atualiza uma role de acordo com o corpo da requisição",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Role atualizada"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Role não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PutMapping
    public ResponseEntity<RoleDTO> update(
        @Valid
        @RequestBody
        UpdateRoleDTO updateRoleDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(RoleDTO.from(roleInteractor.update(updateRoleDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Obter roles",
        description = "Obtém todas as roles",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Roles obtidas"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAllRoles(
        Pageable pageable
    ) {
        List<RoleDTO> roles = roleInteractor.getAllRoles(pageable.getPageNumber(), pageable.getPageSize()).stream()
            .map(RoleDTO::from)
            .toList();
        Page<RoleDTO> rolesPage = new PageImpl<>(roles, pageable, roles.size());
        return ResponseEntity.status(HttpStatus.OK).body(rolesPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Obter role por ID",
        description = "Obtém uma role de acordo com o ID no parâmetro UUID",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Role obtida"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Role não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<RoleDTO> getRoleById(
        @Parameter(
            description = "Identificador da role",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(RoleDTO.from(roleInteractor.findById(UUID)));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Deletar role",
        description = "Deleta uma role de acordo com o ID no parâmetro UUID",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Role deletada"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Token de acesso inválido ou expirado",
                content = @Content(
                    schema = @Schema(implementation = Void.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Role não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> deleteRoleById(
        @Parameter(
            description = "Identificador da role",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID
    ) {
        roleInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Obter roles de usuário",
        description = "Obtém todas as roles de acordo com o ID do usuário no parâmetro UUID",
        tags = "Roles",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Roles obtidas"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping("/users/{userUUID}")
    public ResponseEntity<List<RoleDTO>> getAllRolesByUserId(
        @Parameter(
            description = "Identificador do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID userUUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleInteractor.getAllRolesByUserId(userUUID).stream()
            .map(RoleDTO::from)
            .toList());
    }
}
