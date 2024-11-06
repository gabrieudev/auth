package com.api.auth.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.auth.rest.dto.ChangeRoleRequest;
import com.api.auth.rest.dto.RoleDTO;
import com.api.auth.service.RoleService;
import com.api.auth.service.UsersRolesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/roles")
public class RoleController {
    
    private final RoleService roleService;
    private final UsersRolesService usersRolesService;

    public RoleController(RoleService roleService, UsersRolesService usersRolesService) {
        this.roleService = roleService;
        this.usersRolesService = usersRolesService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Assign role",
        description = "Assign role to user",
        tags = "Roles"
    )
    @PostMapping("/assign")
    public ResponseEntity<Void> assign(@Valid @RequestBody ChangeRoleRequest changeRoleRequest) {
        usersRolesService.assign(changeRoleRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Unassign role",
        description = "Unassign role from user",
        tags = "Roles"
    )
    @DeleteMapping("/unassign")
    public ResponseEntity<Void> unassign(@Valid @RequestBody ChangeRoleRequest changeRoleRequest) {
        usersRolesService.unassign(changeRoleRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Get by id",
        description = "Get role by id",
        tags = "Roles"
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<RoleDTO> getById(@PathVariable("UUID") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getById(id));
    }
    
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Get by user",
        description = "Get roles by user",
        tags = "Roles"
    )
    @GetMapping("/user")
    public ResponseEntity<List<RoleDTO>> getByUser(@RequestParam("userId") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getByUser(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Get all",
        description = "Get all roles",
        tags = "Roles"
    )
    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll(pageable));
    }
    
}
