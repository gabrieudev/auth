package br.com.gabrieudev.auth.adapters.input.rest;

import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;
import br.com.gabrieudev.auth.application.exceptions.AlreadyExistsException;
import br.com.gabrieudev.auth.application.exceptions.BadCredentialsException;
import br.com.gabrieudev.auth.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.auth.application.exceptions.EmailException;
import br.com.gabrieudev.auth.application.exceptions.InternalErrorException;
import br.com.gabrieudev.auth.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.auth.application.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleUserAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInvalidTokenException(InvalidTokenException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(e.getMessage(), 500);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponseDTO<String> apiResponse = ApiResponseDTO.error(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), 400);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleBusinessRuleException(BusinessRuleException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleEmailException(EmailException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInternalErrorException(InternalErrorException e) {
        return new ResponseEntity<>(e.toApiResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
