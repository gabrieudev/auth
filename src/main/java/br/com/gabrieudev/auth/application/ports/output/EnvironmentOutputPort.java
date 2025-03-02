package br.com.gabrieudev.auth.application.ports.output;

public interface EnvironmentOutputPort {
    String getApiBaseUrl();
    String getFrontendResetPasswordUrl();
    Integer getAccessTokenExpiration();
    Integer getRefreshTokenExpiration();
}
