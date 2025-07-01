package com.appointmentservice.appointment.exception;

public class SlotCapacityExceededException extends RuntimeException {
    public SlotCapacityExceededException(String message) {
        super(message);
    }
}