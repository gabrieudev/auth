package com.api.auth.config.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.UsersRoles;
import com.api.auth.repository.RoleRepository;
import com.api.auth.repository.UserRepository;
import com.api.auth.repository.UsersRolesRepository;

@Configuration
@Profile("dev")
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UsersRolesRepository usersRolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DatabaseLoader(UserRepository userRepository, UsersRolesRepository usersRolesRepository,
            BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User user = new User();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role userRole = new Role();
            userRole.setName("USER");

            UsersRoles adminUsersRoles = new UsersRoles();
            adminUsersRoles.setRole(adminRole);
            adminUsersRoles.setUser(user);
            UsersRoles userUsersRoles = new UsersRoles();
            userUsersRoles.setRole(userRole);
            userUsersRoles.setUser(user);

            userRepository.save(user);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            usersRolesRepository.save(userUsersRoles);
            usersRolesRepository.save(adminUsersRoles);
        }
    }
}
