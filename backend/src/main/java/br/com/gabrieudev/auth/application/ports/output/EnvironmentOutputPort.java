package br.com.gabrieudev.auth.application.ports.output;

public interface EnvironmentOutputPort {
    String getApiBaseUrl();
    Integer getAccessTokenExpiration();
    Integer getRefreshTokenExpiration();
}
