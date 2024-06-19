package com.api.auth.dto;

public record Email(
        String to,
        String subject,
        String body
) {
}
