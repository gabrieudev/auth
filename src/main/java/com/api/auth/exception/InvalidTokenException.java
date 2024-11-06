package com.api.auth.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
    
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, super.getLocalizedMessage());
        problemDetail.setTitle("Invalid token");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", super.getStackTrace());
        return problemDetail;
    }
}
