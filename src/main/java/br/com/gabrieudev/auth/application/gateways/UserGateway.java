package br.com.gabrieudev.auth.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.auth.domain.entities.User;

public interface UserGateway {
    User signup(User user);
    User findByEmail(String email);
    User findById(UUID id);
    User findByToken(String token);
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
    User update(User user);
    void delete(UUID id);
    List<User> getAllUsers(Integer page, Integer size);
    List<User> search(String param, Integer page, Integer size);
    String encode(String password);
    boolean matches(String rawPassword, String encryptedPassword);
}