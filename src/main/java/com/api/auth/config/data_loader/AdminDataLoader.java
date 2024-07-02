package com.api.diceRoller.config.data_loader;

import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.enums.RoleEnum;
import com.api.auth.repository.RoleRepository;
import com.api.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@Transactional
public class AdminDataLoader implements CommandLineRunner {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            Role role = new Role();
            role.setId(roleEnum.getId());
            role.setRole(roleEnum);
            roleRepository.save(role);
        }
        String emailAdmin = "admin@gmail.com";
        Optional<User> userOptional = userRepository.findByEmail(emailAdmin);
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(emailAdmin);
            String passwordAdmin = "admin";
            user.setPassword(bCryptPasswordEncoder.encode(passwordAdmin));
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            user.setRoles(roles);
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setName("admin");
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
