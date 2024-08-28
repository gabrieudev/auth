package com.api.auth.service;

import com.api.auth.dto.*;
import com.api.auth.exception.EntityNotFoundException;
import com.api.auth.exception.UserAlreadyExistsException;
import com.api.auth.model.ConfirmationToken;
import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.repository.ConfirmationTokenRepository;
import com.api.auth.repository.RoleRepository;
import com.api.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Value("${app.base_url}")
    private String baseUrl;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenService tokenService;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, EmailService emailService, ConfirmationTokenRepository confirmationTokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User(registerDTO);
        Role role = roleRepository.findByName("BASIC").orElseThrow();
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        user.setEnabled(false);
        user.setRoles(Set.of(role));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setCreatedAt(Instant.now());
        confirmationToken.setExpiresAt(Instant.now().plusSeconds(600));
        confirmationTokenRepository.save(confirmationToken);

        String link = baseUrl + "/users/confirm?token=" + confirmationToken.getToken();
        Email email = new Email(
                user.getEmail(),
                "Email confirm",
                "Link to confirm your registration: " + link
        );
        emailService.send(email);
    }

    public TokenDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email"));
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        String token = tokenService.generateToken(user);
        return new TokenDTO(token, Instant.now().plusSeconds(300));
    }

    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        User user = confirmationToken.getUser();
        if (confirmationToken.getExpiresAt().isBefore(Instant.now())) {
            throw new AccessDeniedException("Token expired");
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void changePassword(PasswordDTO passwordDTO) {
        User user = userRepository.findByEmail(passwordDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email"));
        if (!bCryptPasswordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        String newEncryptedPassword = bCryptPasswordEncoder.encode(passwordDTO.getNewPassword());
        user.setPassword(newEncryptedPassword);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }

    public UserDTO getById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id: " + userId));
        return new UserDTO(user);
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this id: " + userId));
        userRepository.delete(user);
    }

    public Page<UserDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::new);
    }

}
