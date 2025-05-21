package com.adminservice.receptionist.exception;

public class ContactPhoneAlreadyExistsException extends RuntimeException {
    public ContactPhoneAlreadyExistsException(String message) {
        super(message);
    }
}
