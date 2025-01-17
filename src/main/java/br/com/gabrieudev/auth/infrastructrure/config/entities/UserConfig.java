package br.com.gabrieudev.auth.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import br.com.gabrieudev.auth.application.gateways.RoleGateway;
import br.com.gabrieudev.auth.application.gateways.UserGateway;
import br.com.gabrieudev.auth.application.gateways.UsersRolesGateway;
import br.com.gabrieudev.auth.application.usecases.UserInteractor;
import br.com.gabrieudev.auth.infrastructrure.gateways.UserServiceGateway;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.UserRepository;

@Configuration
public class UserConfig {
    @Bean
    UserInteractor userInteractor(UserGateway userGateway, RoleGateway roleGateway, UsersRolesGateway usersRolesGateway) {
        return new UserInteractor(userGateway, roleGateway, usersRolesGateway);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtDecoder jwtDecoder) {
        return new UserServiceGateway(userRepository, passwordEncoder, jwtDecoder);
    }
}
