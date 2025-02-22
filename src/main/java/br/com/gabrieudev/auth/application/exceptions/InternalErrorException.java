package br.com.gabrieudev.auth.application.exceptions;

import java.time.LocalDateTime;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(500, this.getMessage(), LocalDateTime.now());
    }
}
