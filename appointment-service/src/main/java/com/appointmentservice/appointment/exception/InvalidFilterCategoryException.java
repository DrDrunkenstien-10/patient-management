package com.appointmentservice.appointment.exception;

public class InvalidFilterCategoryException extends RuntimeException {
    public InvalidFilterCategoryException(String message) {
        super(message);
    }
}