package br.com.gabrieudev.auth.infrastructrure.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        
        config.addAllowedOriginPattern("http://localhost:8080");

        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
