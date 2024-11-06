package com.api.auth.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @Schema(description = "JWT token")
    private String accessToken;

    @Schema(description = "Refresh token")
    private String refreshToken;
}
