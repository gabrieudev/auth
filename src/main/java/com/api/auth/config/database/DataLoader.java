package com.api.auth.config.database;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
@Transactional
public class DataLoader implements CommandLineRunner {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(RoleEnum.values())
                .map(roleEnum -> new Role(roleEnum.getId(), roleEnum.getRole()))
                .forEach(roleRepository::save);

        Optional<User> userOptional = userRepository.findByEmail(adminEmail);
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(bCryptPasswordEncoder.encode(adminPassword));
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
