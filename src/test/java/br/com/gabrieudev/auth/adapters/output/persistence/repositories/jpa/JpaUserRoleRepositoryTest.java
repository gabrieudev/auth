package br.com.gabrieudev.auth.adapters.output.persistence.repositories.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabrieudev.auth.adapters.output.persistence.entities.JpaRoleEntity;
import br.com.gabrieudev.auth.adapters.output.persistence.entities.JpaUserEntity;
import br.com.gabrieudev.auth.adapters.output.persistence.entities.JpaUserRoleEntity;

@DataJpaTest
@ActiveProfiles("test")
public class JpaUserRoleRepositoryTest {
    @Autowired
    private JpaUserRoleRepository repository;
    
    @Autowired
    private JpaRoleRepository roleRepository;
    
    @Autowired
    private JpaUserRepository userRepository;

    private JpaRoleEntity createRole(JpaRoleEntity role) {
        return roleRepository.save(role);
    }

    private JpaUserEntity createUser(JpaUserEntity user) {
        return userRepository.save(user);
    }

    private JpaUserRoleEntity createUserRole(JpaUserRoleEntity userRole) {
        return repository.save(userRole);
    }

    @Test
    @DisplayName("Should exists by user and role")
    public void shouldExistsByUserAndRole() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaRoleEntity createdRole = createRole(role);
        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        boolean exists = repository.existsByUserAndRole(user, role);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should not exists by user and role")
    public void shouldNotExistsByUserAndRole() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaRoleEntity createdRole = createRole(role);
        JpaUserEntity createdUser = createUser(user);

        boolean exists = repository.existsByUserAndRole(createdUser, createdRole);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should find by user")
    public void shouldFindByUser() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaRoleEntity createdRole = createRole(role);
        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        List<JpaUserRoleEntity> result = repository.findByUser(createdUser);

        assertThat(result.size()).isEqualTo(1);
        assertEquals(result.get(0).getRole().getName(), "ROLE_USER");
    }

    @Test
    @DisplayName("Should not find by user")
    public void shouldNotFindByUser() {
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaUserEntity createdUser = createUser(user);

        List<JpaUserRoleEntity> result = repository.findByUser(createdUser);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should exists by role")
    public void shouldExistsByRole() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);

        JpaRoleEntity createdRole = createRole(role);

        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        boolean exists = repository.existsByRole(createdRole);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should not exists by role")
    public void shouldNotExistsByRole() {
        boolean exists = repository.existsByRole(new JpaRoleEntity(UUID.randomUUID(), "ROLE_USER", null));

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should delete by user and role")
    public void shouldDeleteByUserAndRole() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaRoleEntity createdRole = createRole(role);
        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        repository.deleteByUserAndRole(createdUser, createdRole);

        assertThat(repository.existsByUserAndRole(createdUser, createdRole)).isFalse();
    }

    @Test
    @DisplayName("Should find by user and role")
    public void shouldFindByUserAndRole() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaRoleEntity createdRole = createRole(role);
        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        Optional<JpaUserRoleEntity> result = repository.findByUserAndRole(createdUser, createdRole);

        assertThat(result.isPresent()).isTrue();
        assertEquals(result.get().getRole().getName(), "ROLE_USER");
    }
}
