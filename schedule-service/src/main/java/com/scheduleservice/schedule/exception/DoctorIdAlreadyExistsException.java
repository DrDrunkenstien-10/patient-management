package com.scheduleservice.schedule.exception;

public class DoctorIdAlreadyExistsException extends RuntimeException {
    public DoctorIdAlreadyExistsException(String message) {
        super(message);
    }
}
