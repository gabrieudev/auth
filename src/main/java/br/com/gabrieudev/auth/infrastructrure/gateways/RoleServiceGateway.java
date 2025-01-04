package br.com.gabrieudev.auth.infrastructrure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.auth.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.auth.application.gateways.RoleGateway;
import br.com.gabrieudev.auth.domain.entities.Role;
import br.com.gabrieudev.auth.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.RoleRepository;

@Service
public class RoleServiceGateway implements RoleGateway {
    private final RoleRepository roleRepository;

    public RoleServiceGateway(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return roleRepository.existsById(id);
    }

    @Override
    @Transactional
    public Role update(Role role) {
        if (!roleRepository.existsById(role.getId())) {
            throw new EntityNotFoundException("Role não encontrada");
        }
        return roleRepository.save(RoleModel.from(role)).toDomainObj();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role não encontrada");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(UUID id) {
        return roleRepository.findById(id)
            .map(RoleModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles(Integer page, Integer size) {
        return roleRepository.findAll(PageRequest.of(page, size))
            .stream()
            .map(RoleModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRolesByUserId(UUID userId) {
        return roleRepository.findAllRolesByUserId(userId)
            .stream()
            .map(RoleModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(RoleModel.from(role)).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return roleRepository.findByName(name)
            .map(RoleModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
