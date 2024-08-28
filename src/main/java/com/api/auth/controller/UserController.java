package com.api.auth.controller;

import com.api.auth.dto.PasswordDTO;
import com.api.auth.dto.UserDTO;
import com.api.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Email confirm",
            description = "Endpoint that, when accessed, confirms the user's email",
            tags = "User"
    )
    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        userService.confirmEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body("Email confirmed successfully");
    }

    @Operation(
            summary = "Get all users",
            description = "Endpoint that gets all users",
            tags = "User"
    )
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(pageable).getContent());
    }

    @Operation(
            summary = "Get user by id",
            description = "Endpoint that gets a user by its id",
            tags = "User"
    )
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<UserDTO> getById(@PathVariable("userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(userId));
    }

    @Operation(
            summary = "Delete user",
            description = "Endpoint that deletes a user by its id",
            tags = "User"
    )
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("userId") UUID userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User removed successfully");
    }

    @Operation(
            summary = "Change password",
            description = "Endpoint that changes a user's password",
            tags = "User"
    )
    @PostMapping("/change-password")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        userService.changePassword(passwordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
    }

}
