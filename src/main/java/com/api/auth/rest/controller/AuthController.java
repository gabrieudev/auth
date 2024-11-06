package com.api.auth.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.auth.rest.dto.LoginRequest;
import com.api.auth.rest.dto.LoginResponse;
import com.api.auth.rest.dto.RefreshRequest;
import com.api.auth.rest.dto.TokenResponse;
import com.api.auth.rest.dto.UserDTO;
import com.api.auth.service.TokenService;
import com.api.auth.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Operation(
        summary = "Login",
        description = "Login user",
        tags = "Auth"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
    }
    
    @Operation(
        summary = "Signup",
        description = "Signup user",
        tags = "Auth"
    )
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDTO));
    }

    @Operation(
        summary = "Refresh",
        description = "Refresh token",
        tags = "Auth"
    )
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.refresh(refreshRequest));        
    }

    @SecurityRequirement(name = "BearerAuth")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @Operation(
        summary = "Validate token",
        description = "Validate token",
        tags = "Auth"
    )
    @PostMapping("/validate-token")
    public ResponseEntity<Void> validate(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        tokenService.validate(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
