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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.auth.application.exceptions.StandardException;
import br.com.gabrieudev.auth.application.usecases.UserInteractor;
import br.com.gabrieudev.auth.application.usecases.UsersRolesInteractor;
import br.com.gabrieudev.auth.domain.entities.Role;
import br.com.gabrieudev.auth.infrastructrure.web.dtos.RoleDTO;
import br.com.gabrieudev.auth.infrastructrure.web.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserInteractor userInteractor;
    private final UsersRolesInteractor usersRolesInteractor;

    public UserController(UserInteractor userInteractor, UsersRolesInteractor usersRolesInteractor) {
        this.userInteractor = userInteractor;
        this.usersRolesInteractor = usersRolesInteractor;
    }

    @Operation(
        summary = "Cadastrar usuário",
        description = "Cadastra um usuário de acordo com o corpo da requisição",
        tags = "Users"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Usuário cadastrado"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Usuário existente",
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
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(
        @Valid 
        @RequestBody 
        UserDTO userDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.from(userInteractor.signup(userDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter por ID",
        description = "Obtém um usuário de acordo com o ID no parâmetro UUID",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário encontrado"
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
    @GetMapping("/{UUID}")
    public ResponseEntity<UserDTO> getUserById(
        @Parameter(
            description = "Identificador do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.from(userInteractor.findById(UUID)));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Pesquisar usuários",
        description = "Pesquisa usuários de acordo com o termo informado no parâmetro q",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuários encontrados"
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
    public ResponseEntity<Page<UserDTO>> search(
        @Parameter(
            description = "Termo de pesquisa",
            example = "João",
            required = true
        )
        @RequestParam String q,
        Pageable pageable
    ) {
        List<UserDTO> users = userInteractor.search(q, pageable.getPageNumber(), pageable.getPageSize()).stream()
            .map(UserDTO::from)
            .toList();
        Page<UserDTO> usersPage = new PageImpl<>(users, pageable, users.size());
        return ResponseEntity.status(HttpStatus.OK).body(usersPage);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza um usuário de acordo com o ID no corpo da requisição",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário atualizado"
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
                description = "Usuário não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Usuário já cadastrado",
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
    public ResponseEntity<UserDTO> update(
        @Valid 
        @RequestBody 
        UserDTO userDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.from(userInteractor.update(userDTO.toDomainObj())));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Deletar usuário",
        description = "Deleta um usuário de acordo com o ID no parâmetro UUID",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário deletado"
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
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(
        @Parameter(
            description = "Identificador do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID
    ) {
        userInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Associar role ao usuário",
        description = "Associa uma role ao usuário de acordo com o ID no parâmetro UUID",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Role associada ao usuário"
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
                description = "Recurso não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Role já associada ao usuário",
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
    @PostMapping("/{UUID}/roles")
    public ResponseEntity<Void> assign(
        @Parameter(
            description = "Identificador do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID,
        @RequestBody 
        List<RoleDTO> roles
    ) {
        List<Role> roleList = roles.stream()
            .map(RoleDTO::toDomainObj)
            .toList();
        usersRolesInteractor.createByUserIdAndRoles(UUID, roleList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Desassociar role ao usuário",
        description = "Desassocia uma role ao usuário de acordo com o ID no parâmetro UUID",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Role desassociada ao usuário"
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
                description = "Recurso não encontrado",
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
    @DeleteMapping("/{UUID}/roles")
    public ResponseEntity<Void> unassign(
        @Parameter(
            description = "Identificador do usuário",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID UUID,
        @RequestBody 
        List<RoleDTO> roles
    ) {
        List<Role> roleList = roles.stream()
            .map(RoleDTO::toDomainObj)
            .toList();
        usersRolesInteractor.deleteByUserIdAndRoles(UUID, roleList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Obter suas informações",
        description = "Obtém suas informações de acordo com o token de acesso",
        tags = "Users",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Informações obtidas"
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
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(
        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").split(" ")[1];
        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.from(userInteractor.findByToken(token)));
    }
}