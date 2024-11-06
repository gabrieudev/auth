package com.api.auth.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.api.auth.exception.EntityNotFoundException;
import com.api.auth.exception.InvalidTokenException;
import com.api.auth.model.Role;
import com.api.auth.model.User;
import com.api.auth.model.UsersRoles;
import com.api.auth.repository.UserRepository;
import com.api.auth.repository.UsersRolesRepository;
import com.api.auth.rest.dto.RefreshRequest;
import com.api.auth.rest.dto.TokenResponse;

@Service
public class TokenService {

    @Value("${spring.application.name}")
    private String issuer;

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final UsersRolesRepository usersRolesRepository;

    public TokenService(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder, UserRepository userRepository, UsersRolesRepository usersRolesRepository) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.usersRolesRepository = usersRolesRepository;
    }

    public String generateAccessToken(User user) {
        List<String> roles = usersRolesRepository.findByUser(user).stream()
                .map(UsersRoles::getRole)
                .map(Role::getName)
                .toList();
        String scopes = String.join(" ", roles);
        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .expiresAt(Instant.now().plusSeconds(600))
                .issuedAt(Instant.now())
                .claim("FirstName", user.getFirstName())
                .claim("LastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("scope", scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken (User user) {
        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .expiresAt(Instant.now().plusSeconds(2592000))
                .issuedAt(Instant.now())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public TokenResponse refresh(RefreshRequest refreshRequest) {
        try {
            var jwt = jwtDecoder.decode(refreshRequest.getRefreshToken());
            User user = userRepository.findById(UUID.fromString(jwt.getSubject()))
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
            String token = generateAccessToken(user);

            return new TokenResponse(token);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    public void validate(String token) {
        try {
            jwtDecoder.decode(token);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }

}
