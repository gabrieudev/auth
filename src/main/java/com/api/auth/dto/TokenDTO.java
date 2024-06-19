package com.api.auth.dto;

import java.time.Instant;

public record TokenDTO(
        String token,
        Instant expiresAt
) {
}
