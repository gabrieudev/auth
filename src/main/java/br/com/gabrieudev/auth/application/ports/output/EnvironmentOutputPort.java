package br.com.gabrieudev.auth.application.ports.output;

public interface EnvironmentOutputPort {
    String getEmailConfirmationUrl();
    Integer getAccessTokenExpiration();
    Integer getRefreshTokenExpiration();
}
