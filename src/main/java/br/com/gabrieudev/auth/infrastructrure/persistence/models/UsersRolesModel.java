package br.com.gabrieudev.auth.infrastructrure.persistence.models;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.auth.domain.entities.UsersRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "UsersRoles")
public class UsersRolesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private RoleModel role;

    public static UsersRolesModel from(UsersRoles usersRoles) {
        return new ModelMapper().map(usersRoles, UsersRolesModel.class);
    }

    public UsersRoles toDomainObj() {
        return new ModelMapper().map(this, UsersRoles.class);
    }
}