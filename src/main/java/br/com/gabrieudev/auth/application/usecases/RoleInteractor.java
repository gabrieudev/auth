package br.com.gabrieudev.auth.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.auth.application.gateways.RoleGateway;
import br.com.gabrieudev.auth.domain.entities.Role;

public class RoleInteractor {
    private final RoleGateway roleGateway;

    public RoleInteractor(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public void delete(UUID id) {
        roleGateway.delete(id);
    }

    public Role save(Role role) {
        return roleGateway.save(role);
    }

    public Role update(Role role) {
        return roleGateway.update(role);
    }

    public Role findById(UUID id) {
        return roleGateway.findById(id);
    }

    public boolean existsById(UUID id) {
        return roleGateway.existsById(id);
    }

    public List<Role> getAllRoles(Integer page, Integer size) {
        return roleGateway.getAllRoles(page, size);
    }

    public List<Role> getAllRolesByUserId(UUID userId) {
        return roleGateway.getAllRolesByUserId(userId);
    }
}
