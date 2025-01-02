package br.com.gabrieudev.auth.infrastructrure.config.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gabrieudev.auth.infrastructrure.persistence.models.RoleModel;
import br.com.gabrieudev.auth.infrastructrure.persistence.models.UserModel;
import br.com.gabrieudev.auth.infrastructrure.persistence.models.UsersRolesModel;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.RoleRepository;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.UserRepository;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.UsersRolesRepository;

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
            UserModel user = new UserModel();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("adminpassword"));

            RoleModel adminRole = new RoleModel();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Role de administrador");
            RoleModel userRole = new RoleModel();
            userRole.setName("USER");
            userRole.setDescription("Role de usuário comum");

            UsersRolesModel adminUsersRoles = new UsersRolesModel();
            adminUsersRoles.setRole(adminRole);
            adminUsersRoles.setUser(user);
            UsersRolesModel userUsersRoles = new UsersRolesModel();
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
