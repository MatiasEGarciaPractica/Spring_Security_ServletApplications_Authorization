package com.authorization.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionC {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ExceptionHandler(Exception e){
        System.err.println(e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    /**
     * When some pre or post authorize fails, throws this exception.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> AccessDeniedExceptionHandler(AccessDeniedException e){
        System.err.println("There was thrown a AccessDeniedException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
