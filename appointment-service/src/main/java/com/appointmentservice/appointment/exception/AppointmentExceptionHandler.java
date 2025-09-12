package com.appointmentservice.appointment.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AppointmentExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDoctorNotFoundException(DoctorNotFoundException ex) {

        log.warn("Doctor not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Doctor not found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {

        log.warn("Patient not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSlotNotFoundException(SlotNotFoundException ex) {

        log.warn("Slot not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Slot not found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SlotCapacityExceededException.class)
    public ResponseEntity<String> handleSlotCapacityExceeded(SlotCapacityExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409 Conflict
                .body(ex.getMessage());
    }

    @ExceptionHandler(AppointmentExistsException.class)
    public ResponseEntity<Map<String, String>> handleAppointExistsException(AppointmentExistsException ex) {
        log.warn("appointment for id exists");

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "appointment for id exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppointNotFoundException(AppointmentNotFoundException ex) {
        log.warn("appointment does not exists");

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "appointment does not exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidFilterCategoryException.class)
    public ResponseEntity<Object> handleInvalidFilterCategory(InvalidFilterCategoryException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAppointmentTimeException(
            InvalidAppointmentTimeException ex) {
        log.warn("Invalid appointment time {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Invalid appointment time");
        return ResponseEntity.badRequest().body(errors);
    }
}
