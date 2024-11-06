package com.api.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, super.getLocalizedMessage());
        problemDetail.setTitle("User already exists");
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        problemDetail.setProperty("stacktrace", super.getStackTrace());
        return problemDetail;
    }
}
