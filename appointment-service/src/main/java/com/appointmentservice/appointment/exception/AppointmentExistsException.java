package com.appointmentservice.appointment.exception;

public class AppointmentExistsException extends RuntimeException {
    public AppointmentExistsException(String message) {
        super(message);
    }
}
