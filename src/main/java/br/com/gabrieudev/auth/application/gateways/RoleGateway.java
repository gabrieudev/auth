package br.com.gabrieudev.auth.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.auth.domain.entities.Role;

public interface RoleGateway {
    Role save(Role role);
    Role update(Role role);
    Role findById(UUID id);
    Role findByName(String name);
    boolean existsById(UUID id);
    void delete(UUID id);
    List<Role> getAllRoles(Integer page, Integer size);
    List<Role> getAllRolesByUserId(UUID userId);
}