package com.scheduleservice.schedule.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ScheduleExeptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ScheduleExeptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        log.warn("Method Argument not valid: {}", ex.getMessage());
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DoctorIdAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDoctorIdAlreadyExistsException(DoctorIdAlreadyExistsException ex) {

        log.warn("Doctor already exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Doctor already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleScheduleNotFoundException(ScheduleNotFoundException ex) {

        log.warn("Schedule already exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Schedule already exists");
        return ResponseEntity.badRequest().body(errors);
    }
}
