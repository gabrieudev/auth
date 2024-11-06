package com.api.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
