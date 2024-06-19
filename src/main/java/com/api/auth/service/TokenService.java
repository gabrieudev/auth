package com.api.auth.service;

import com.api.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TokenService {

    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        List<String> roles = user.getRoles().stream().map(
                role -> role.getRole().getRole()
        ).toList();
        String scopes = String.join(" ", roles);
        var claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .expiresAt(Instant.now().plusSeconds(300))
                .issuedAt(Instant.now())
                .claim("email", user.getEmail())
                .claim("scope", scopes)
        .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
