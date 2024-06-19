package com.api.auth.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    BASIC(2L, "BASIC"),
    ADMIN(1L, "ADMIN");

    private final Long id;
    private final String role;
}
