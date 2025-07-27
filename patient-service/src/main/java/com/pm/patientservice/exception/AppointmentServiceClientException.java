package com.pm.patientservice.exception;

public class AppointmentServiceClientException extends RuntimeException {
    public AppointmentServiceClientException(String message) {
        super(message);
    }

    public AppointmentServiceClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
