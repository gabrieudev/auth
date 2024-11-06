package com.api.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(
        value = """
                SELECT * FROM Users
                WHERE
                    email LIKE :p1
                    OR firstName LIKE :p1
                    OR lastName LIKE :p1
                """,
        nativeQuery = true
    )
    Page<User> search(@Param("p1") String param, Pageable pageable);
}
