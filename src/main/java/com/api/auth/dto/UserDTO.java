package com.api.auth.dto;

import com.api.auth.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private Set<Role> roles;

    private Instant createdAt;

    private Instant updatedAt;

    private boolean enabled;

}
