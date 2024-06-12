package com.hutech.furniturestore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation failed");
        response.put("data", validationErrors);
        response.put("dateTime", LocalDateTime.now().format(formatter));
        response.put("messageConstants", "Validation errors occurred");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(CustomException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("dateTime", LocalDateTime.now().format(formatter));
        response.put("messageConstants", "A custom error occurred");
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementFoundException(NoSuchElementFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("data", null);
        response.put("dateTime", LocalDateTime.now().format(formatter));
        response.put("messageConstants", "Element not found");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "An unexpected error occurred");
        response.put("data", null);
        response.put("dateTime", LocalDateTime.now().format(formatter));
        response.put("messageConstants", "System error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
