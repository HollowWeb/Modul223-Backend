package org.example.modul223backend.exception;

import org.example.modul223backend.exception.RoleException.RoleException;
import org.example.modul223backend.exception.UserException.UnauthorizedActionException;
import org.example.modul223backend.exception.UserException.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", ex.getStatus().value());

        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<Object> handleRoleException(RoleException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", ex.getStatus().value());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", "An unexpected error occurred.");
        errorDetails.put("status", 500);

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());

        // Extract field-specific validation errors
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));

        errorDetails.put("message", "Validation failed");
        errorDetails.put("errors", fieldErrors);
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Object> handleUnauthorizedAction(UnauthorizedActionException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", ex.getStatus().value());

        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

}
