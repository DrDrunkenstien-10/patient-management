package com.pm.patientservice.exception;

public class ContactPhoneAlreadyExistsException extends RuntimeException {
    public ContactPhoneAlreadyExistsException(String message) {
        super(message);
    }
}
