package com.api.auth.dto;

import com.api.auth.model.Role;
import com.api.auth.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public UserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(user, this);
    }

}
