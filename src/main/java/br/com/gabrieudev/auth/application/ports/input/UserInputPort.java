package br.com.gabrieudev.auth.application.ports.input;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.auth.domain.User;

public interface UserInputPort {
    User create(User user);
    User update(User user);
    void delete(UUID id);
    User findById(UUID id);
    User getMe(String token);
    List<User> findAll(String email, String param, Integer page, Integer size);
    void confirmEmail(UUID code);
    void sendConfirmationEmail(UUID id);
}
