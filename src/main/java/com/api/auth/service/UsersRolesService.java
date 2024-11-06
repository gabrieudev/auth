package com.api.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.auth.exception.EntityNotFoundException;
import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.UsersRoles;
import com.api.auth.repository.RoleRepository;
import com.api.auth.repository.UserRepository;
import com.api.auth.repository.UsersRolesRepository;
import com.api.auth.rest.dto.ChangeRoleRequest;

@Service
public class UsersRolesService {
    
    private final UsersRolesRepository usersRolesRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UsersRolesService(UsersRolesRepository usersRolesRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.usersRolesRepository = usersRolesRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void assign(ChangeRoleRequest changeRoleRequest) {
        UsersRoles usersRoles = new UsersRoles();

        User user = userRepository.findById(changeRoleRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(changeRoleRequest.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        usersRoles.setUser(user);
        usersRoles.setRole(role);

        usersRolesRepository.save(usersRoles);
    }

    @Transactional
    public void unassign(ChangeRoleRequest changeRoleRequest) {
        User user = userRepository.findById(changeRoleRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(changeRoleRequest.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        usersRolesRepository.deleteByUserAndRole(user, role);
    }
}
