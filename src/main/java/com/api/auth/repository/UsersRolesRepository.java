package com.api.auth.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.UsersRoles;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, UUID> {
    List<UsersRoles> findByUser(User user);

    void deleteByUserAndRole(User user, Role role);
}
