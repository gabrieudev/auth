package br.com.gabrieudev.auth.domain.entities;

import java.util.UUID;

public class UsersRoles {
    private UUID id;
    private User user;
    private Role role;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    
    public UsersRoles(UUID id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UsersRoles() {
    }
}
