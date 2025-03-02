package br.com.gabrieudev.auth.adapters.output.persistence.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.gabrieudev.auth.application.ports.output.EnvironmentOutputPort;

@Component
public class EnvironmentRepositoryAdapter implements EnvironmentOutputPort {
    @Value("${api.base-url}")
    private String apiBaseUrl;
    @Value("${jwt.access-token.minutes}")
    private Integer accessTokenExpiration;
    @Value("${jwt.refresh-token.minutes}")
    private Integer refreshTokenExpiration;
    @Value("${frontend.reset-password.url}")
    private String frontendResetPasswordUrl;

    @Override
    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    @Override
    public Integer getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    @Override
    public Integer getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    @Override
    public String getFrontendResetPasswordUrl() {
        return frontendResetPasswordUrl;
    }
}
