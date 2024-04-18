package com.authorization.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionC {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> AccessDeniedExceptionHandler(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("access denied");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> BadCredentialsExceptionHandler(BadCredentialsException e){
        return ResponseEntity.badRequest().body("Wrong credentials");
    }
}
