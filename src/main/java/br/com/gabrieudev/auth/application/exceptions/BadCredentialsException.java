package br.com.gabrieudev.auth.application.exceptions;

import java.time.LocalDateTime;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(400, this.getMessage(), LocalDateTime.now());
    }
}
