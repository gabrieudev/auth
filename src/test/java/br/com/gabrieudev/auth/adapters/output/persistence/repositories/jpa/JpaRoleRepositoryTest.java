package br.com.gabrieudev.auth.adapters.output.persistence.repositories.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

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
public class JpaRoleRepositoryTest {
    @Autowired
    private JpaRoleRepository roleRepository;

    @Autowired
    private JpaUserRoleRepository userRoleRepository;

    @Autowired
    private JpaUserRepository userRepository;

    private JpaRoleEntity createRole(JpaRoleEntity role) {
        return roleRepository.save(role);
    }

    private JpaUserEntity createUser(JpaUserEntity user) {
        return userRepository.save(user);
    }

    private JpaUserRoleEntity createUserRole(JpaUserRoleEntity userRole) {
        return userRoleRepository.save(userRole);
    }

    @Test
    @DisplayName("Should find role by name")
    public void shouldFindRoleByName() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);
        
        createRole(role);

        JpaRoleEntity found = roleRepository.findByName("ROLE_USER").get();

        assertThat(found).isNotNull();
        assertEquals(found.getName(), "ROLE_USER");
    }

    @Test
    @DisplayName("Should not find role by name")
    public void shouldNotFindRoleByName() {
        JpaRoleEntity found = roleRepository.findByName("ROLE_USER").orElse(null);

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Should exists role by name")
    public void shouldExistsRoleByName() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);

        createRole(role);

        boolean exists = roleRepository.existsByName("ROLE_USER");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should not exists role by name")
    public void shouldNotExistsRoleByName() {
        boolean exists = roleRepository.existsByName("ROLE_USER");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should find all roles")
    public void shouldFindAllRoles() {
        JpaRoleEntity role = new JpaRoleEntity(null, "ROLE_USER", null);

        JpaRoleEntity createdRole = createRole(role);

        JpaUserEntity user = new JpaUserEntity(null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        JpaUserEntity createdUser = createUser(user);

        JpaUserRoleEntity userRole = new JpaUserRoleEntity(null, createdUser, createdRole);

        createUserRole(userRole);

        List<JpaRoleEntity> firstResult = roleRepository.findAll(null, "ROLE_USER");

        List<JpaRoleEntity> secondResult = roleRepository.findAll(createdUser.getId(), null);

        assertThat(firstResult.size()).isEqualTo(1);
        assertThat(secondResult.size()).isEqualTo(1);
        assertEquals(firstResult.get(0).getName(), "ROLE_USER");
        assertEquals(secondResult.get(0).getName(), "ROLE_USER");
    }
}
