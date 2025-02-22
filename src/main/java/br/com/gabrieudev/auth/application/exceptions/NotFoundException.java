package br.com.gabrieudev.auth.application.exceptions;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(404, this.getMessage(), LocalDateTime.now());
    }
}
