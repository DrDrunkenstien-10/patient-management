package com.adminservice.doctor.exception;

public class LicenseNumberAlreadyExistsException extends RuntimeException {

    public LicenseNumberAlreadyExistsException(String message){
        super(message);
    }
}