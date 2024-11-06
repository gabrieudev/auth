package com.api.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.auth.exception.EntityNotFoundException;
import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.UsersRoles;
import com.api.auth.repository.RoleRepository;
import com.api.auth.repository.UserRepository;
import com.api.auth.repository.UsersRolesRepository;
import com.api.auth.rest.dto.RoleDTO;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;
    private final UsersRolesRepository usersRolesRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UsersRolesRepository usersRolesRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public RoleDTO getById(UUID id) {
        return roleRepository.findById(id)
            .map(Role::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Transactional
    public RoleDTO create(RoleDTO roleDTO) {
        Role role = roleDTO.toModel();
        return roleRepository.save(role).toDto();
    }

    @Transactional
    public RoleDTO update(RoleDTO roleDTO) {
        if (!roleRepository.existsById(roleDTO.getId())) {
            throw new EntityNotFoundException("Role not found");
        }
        Role role = roleDTO.toModel();
        return roleRepository.save(role).toDto();
    }

    @Transactional
    public void delete(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RoleDTO> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable)
            .map(Role::toDto);
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> getByUser(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return usersRolesRepository.findByUser(user)
            .stream()
            .map(UsersRoles::getRole)
            .map(Role::toDto)
            .toList();
    }

}
