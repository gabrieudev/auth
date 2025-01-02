package br.com.gabrieudev.auth.infrastructrure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.auth.application.gateways.RoleGateway;
import br.com.gabrieudev.auth.application.usecases.RoleInteractor;
import br.com.gabrieudev.auth.infrastructrure.gateways.RoleServiceGateway;
import br.com.gabrieudev.auth.infrastructrure.persistence.repositories.RoleRepository;

@Configuration
public class RoleConfig {
    @Bean
    RoleInteractor roleInteractor(RoleGateway roleGateway) {
        return new RoleInteractor(roleGateway);
    }

    @Bean
    RoleGateway roleGateway(RoleRepository roleRepository) {
        return new RoleServiceGateway(roleRepository);
    }
}
