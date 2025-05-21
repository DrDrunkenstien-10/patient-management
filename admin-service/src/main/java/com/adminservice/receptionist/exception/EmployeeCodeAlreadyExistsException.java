package com.adminservice.receptionist.exception;

public class EmployeeCodeAlreadyExistsException extends RuntimeException {
    public EmployeeCodeAlreadyExistsException(String message) {
        super(message);
    }
}
