package br.com.gabrieudev.auth.adapters.output.persistence.repositories.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import br.com.gabrieudev.auth.adapters.output.persistence.entities.JpaUserEntity;

@DataJpaTest
@ActiveProfiles("test")
public class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository repository;

    private JpaUserEntity create(JpaUserEntity user) {
        return repository.save(user);
    }

    @Test
    @DisplayName("Should exists by email")
    public void shouldExistsByEmail() {
        JpaUserEntity user = new JpaUserEntity(
                null, "John",
                "Paul",
                "john@gmail.com",
                "123456", true,
                LocalDateTime.now(),
                LocalDateTime.now());

        create(user);

        boolean exists = repository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should not exists by email")
    public void shouldNotExistsByEmail() {
        boolean exists = repository.existsByEmail("john@gmail.com");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should find by email")
    public void shouldFindByEmail() {
        JpaUserEntity user = new JpaUserEntity(
                null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        create(user);

        JpaUserEntity found = repository.findByEmail(user.getEmail()).get();

        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("Should not find by email")
    public void shouldNotFindByEmail() {
        JpaUserEntity found = repository.findByEmail("john@gmail.com").orElse(null);

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Should find all with pagination and filters")
    public void shouldFindAllWithPaginationAndFilters() {
        JpaUserEntity firstUser = new JpaUserEntity(
                null, "John", "Paul",
                "john@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        create(firstUser);

        JpaUserEntity secondUser = new JpaUserEntity(
                null, "Mary", "Christine",
                "mary@gmail.com", "123456", true,
                LocalDateTime.now(), LocalDateTime.now());

        create(secondUser);

        Pageable pageable = PageRequest.of(0, 10);

        Page<JpaUserEntity> firstPage = repository.findAll(null, "john@gmail.com", pageable);

        Page<JpaUserEntity> secondPage = repository.findAll("mary", null, pageable);

        assertThat(firstPage.getContent().size()).isEqualTo(1);
        assertThat(secondPage.getContent().size()).isEqualTo(1);
        assertEquals(firstPage.getContent().get(0).getEmail(), "john@gmail.com");
        assertEquals(secondPage.getContent().get(0).getEmail(), "mary@gmail.com");
    }
}