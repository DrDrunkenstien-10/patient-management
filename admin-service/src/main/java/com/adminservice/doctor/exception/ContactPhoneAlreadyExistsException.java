package com.adminservice.doctor.exception;

public class ContactPhoneAlreadyExistsException extends RuntimeException {
    public ContactPhoneAlreadyExistsException(String message) {
        super(message);
    }
}
