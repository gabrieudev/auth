package br.com.gabrieudev.auth.infrastructrure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.auth.infrastructrure.persistence.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    @Query(
        value = """
                SELECT r.* 
                FROM UsersRoles ur
                INNER JOIN Roles r 
                    ON ur.roleId = r.id
                WHERE ur.userId = :userId
                """,
        nativeQuery = true
    )
    List<RoleModel> findAllRolesByUserId(@Param("userId") UUID userId);

    Optional<RoleModel> findByName(String name);

    boolean existsByName(String name);
}