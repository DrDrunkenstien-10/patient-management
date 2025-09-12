package com.appointmentservice.appointment.exception;

public class InvalidAppointmentTimeException extends RuntimeException {

    public InvalidAppointmentTimeException(String message) {
        super(message);
    }

    public InvalidAppointmentTimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
