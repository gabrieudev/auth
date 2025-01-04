package br.com.gabrieudev.auth.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.auth.application.exceptions.EntityAlreadyExistsException;
import br.com.gabrieudev.auth.application.gateways.RoleGateway;
import br.com.gabrieudev.auth.application.gateways.UserGateway;
import br.com.gabrieudev.auth.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.auth.domain.entities.Role;
import br.com.gabrieudev.auth.domain.entities.User;
import br.com.gabrieudev.auth.domain.entities.UsersRoles;

public class UserInteractor {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;
    private final UsersRolesGateway usersRolesGateway;

    public UserInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
        this.usersRolesGateway = usersRolesGateway;
    }

    public User signup(User user) {
        if (userGateway.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("E-mail já cadastrado");
        }

        user.setPassword(userGateway.encode(user.getPassword()));
        
        User createdUser = userGateway.signup(user);

        Role basicRole = roleGateway.findByName("USER");
        
        usersRolesGateway.create(new UsersRoles(null, createdUser, basicRole));
        
        return createdUser;
    }

    public User findById(UUID id) {
        return userGateway.findById(id);
    }

    public User update(User user) {
        User userToUpdate = userGateway.findById(user.getId());

        if (!userToUpdate.getEmail().equals(user.getEmail()) && userGateway.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("E-mail já cadastrado");
        }

        user.setPassword(userGateway.encode(user.getPassword()));

        return userGateway.update(user);
    }

    public void delete(UUID id) {
        userGateway.delete(id);
    }

    public List<User> getAllUsers(Integer page, Integer size) {
        return userGateway.getAllUsers(page, size);
    }

    public List<User> search(String param, Integer page, Integer size) {
        return userGateway.search(param, page, size);
    }

    public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userGateway.existsByEmail(email);
    }

    public User findByToken(String token) {
        return userGateway.findByToken(token);
    }
}
