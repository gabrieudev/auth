package br.com.gabrieudev.auth.config.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.gabrieudev.auth.application.ports.input.AuthInputPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    private final AuthInputPort authInputPort;

    public TokenValidationFilter(AuthInputPort authInputPort) {
        this.authInputPort = authInputPort;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").split(" ")[1];

            if (!authInputPort.isValidToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invático");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
