package br.com.gabrieudev.auth.application.ports.input;

import java.util.UUID;

import br.com.gabrieudev.auth.domain.UserRole;

public interface UserRoleInputPort {
    UserRole assign(UUID userId, UUID roleId);
    void unassign(UUID userId, UUID roleId);
}
