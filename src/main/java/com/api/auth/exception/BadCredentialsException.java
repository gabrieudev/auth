package com.api.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String message) {
        super(message);
    }
    
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, super.getLocalizedMessage());
        problemDetail.setTitle("Bad credentials");
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        problemDetail.setProperty("stacktrace", super.getStackTrace());
        return problemDetail;
    }
    
}
