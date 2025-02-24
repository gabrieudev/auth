package br.com.gabrieudev.auth.adapters.input.rest.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.auth.LoginRequest;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.auth.LoginResponse;
import br.com.gabrieudev.auth.adapters.input.rest.dtos.auth.RefreshTokenRequest;
import br.com.gabrieudev.auth.application.ports.input.AuthInputPort;
import br.com.gabrieudev.auth.domain.Tokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthInputPort authInputPort;
    @Value("${jwt.refresh-token.minutes}")
    private Integer refreshTokenExpiration;
    @Value("${jwt.access-token.minutes}")
    private Integer accessTokenExpiration;

    public AuthController(AuthInputPort authInputPort) {
        this.authInputPort = authInputPort;
    }

    @Operation(
        summary = "Login",
        description = "Endpoint para login de um usuário.",
        tags = { "Auth" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário logado com sucesso."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Usuário ou senha inválidos."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor."
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> login(
        @Valid
        @RequestBody
        LoginRequest loginRequest
    ) {
        Tokens tokens = authInputPort.login(loginRequest.getEmail(), loginRequest.getPassword());

        LoginResponse loginResponse = new LoginResponse(tokens.getAccessToken(), tokens.getRefreshToken(), Instant.now().plusSeconds(refreshTokenExpiration * 60), Instant.now().plusSeconds(accessTokenExpiration * 60));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(loginResponse));
    }

    @Operation(
        summary = "Logout",
        description = "Endpoint para logout de um usuário.",
        tags = { "Auth" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário deslogado com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Refresh token inválido."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor."
            )
        }
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<String>> logout(
        @Valid
        @RequestBody
        RefreshTokenRequest refreshTokenRequest
    ) {
        authInputPort.logout(refreshTokenRequest.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok("Logout realizado com sucesso."));
    }

    @Operation(
        summary = "Atualizar tokens",
        description = "Endpoint para atualizar os tokens.",
        tags = { "Auth" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tokens atualizados com sucesso."
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Refresh token inválido."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor."
            )
        }
    )
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO<LoginResponse>> refresh(
        @Valid
        @RequestBody
        RefreshTokenRequest refreshTokenRequest
    ) {
        Tokens tokens = authInputPort.refreshTokens(refreshTokenRequest.getRefreshToken());

        LoginResponse loginResponse = new LoginResponse(tokens.getAccessToken(), tokens.getRefreshToken(), Instant.now().plusSeconds(refreshTokenExpiration * 60), Instant.now().plusSeconds(accessTokenExpiration * 60));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.ok(loginResponse));
    }
}
