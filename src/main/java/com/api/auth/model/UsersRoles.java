package com.api.auth.model;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.api.auth.rest.dto.UsersRolesDTO;

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
public class UsersRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public UsersRolesDTO toDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsersRolesDTO.class);
    }
}
