package com.adminservice.receptionist.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReceptionistExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ReceptionistExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmployeeCodeAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeCodeAlreadyExistsException(
            EmployeeCodeAlreadyExistsException ex) {
        log.warn("Employee code already exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Employee code already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        log.warn("Email already exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ContactPhoneAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleContactPhoneAlreadyExistsException(
            ContactPhoneAlreadyExistsException ex) {

        log.warn("Contact phone already exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Contact phone already exists");
        return ResponseEntity.badRequest().body(errors);
    }
}
