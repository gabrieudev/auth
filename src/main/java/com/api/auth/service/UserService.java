package com.api.auth.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.auth.exception.BadCredentialsException;
import com.api.auth.exception.EntityNotFoundException;
import com.api.auth.exception.UserAlreadyExistsException;
import com.api.auth.model.User;
import com.api.auth.repository.UserRepository;
import com.api.auth.rest.dto.LoginRequest;
import com.api.auth.rest.dto.LoginResponse;
import com.api.auth.rest.dto.UserDTO;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserDTO getById(UUID id) {
        return userRepository.findById(id)
            .map(User::toDto)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Invalid email"));
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);
        return new LoginResponse(token, refreshToken);
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userDTO.toModel()).toDto();
    }

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));        
        return userRepository.save(userDTO.toModel()).toDto();
    }

    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> search(String param, Pageable pageable) {
        return userRepository.search(param, pageable)
            .map(User::toDto);
    }

}
