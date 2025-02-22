package br.com.gabrieudev.auth.adapters.input.rest;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gabrieudev.auth.application.exceptions.AlreadyExistsException;
import br.com.gabrieudev.auth.application.exceptions.BadCredentialsException;
import br.com.gabrieudev.auth.application.exceptions.BusinessRuleException;
import br.com.gabrieudev.auth.application.exceptions.EmailException;
import br.com.gabrieudev.auth.application.exceptions.InternalErrorException;
import br.com.gabrieudev.auth.application.exceptions.InvalidTokenException;
import br.com.gabrieudev.auth.application.exceptions.NotFoundException;
import br.com.gabrieudev.auth.application.exceptions.StandardException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardException> handleUserNotFoundException(NotFoundException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardException> handleBadCredentialsException(BadCredentialsException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<StandardException> handleUserAlreadyExistsException(AlreadyExistsException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StandardException> handleInvalidTokenException(InvalidTokenException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StandardException standardException = new StandardException(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandardException> handleBusinessRuleException(BusinessRuleException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<StandardException> handleEmailException(EmailException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<StandardException> handleInternalErrorException(InternalErrorException e) {
        StandardException standardException = e.toStandardException();
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
