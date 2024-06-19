package com.api.auth.repository;

import com.api.auth.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {
}
