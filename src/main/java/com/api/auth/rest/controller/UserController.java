package com.api.auth.rest.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.auth.rest.dto.UserDTO;
import com.api.auth.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Get user by ID",
        description = "Get user by ID",
        tags = "Users"
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<UserDTO> getById(@PathVariable("UUID") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }
    
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(
        summary = "Search users",
        description = "Search users by first name, last name or email",
        tags = "Users"
    )
    @GetMapping
    public ResponseEntity<Page<UserDTO>> search(@RequestParam("argument") String argument, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.search(argument, pageable));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Update user",
        description = "Update user",
        tags = "Users"
    )
    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDTO));
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Delete user",
        description = "Delete user",
        tags = "Users"
    )
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(@PathVariable("UUID") UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
