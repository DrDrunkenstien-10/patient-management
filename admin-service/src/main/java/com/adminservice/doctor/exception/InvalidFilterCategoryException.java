package com.adminservice.doctor.exception;

public class InvalidFilterCategoryException extends RuntimeException {
    public InvalidFilterCategoryException(String message) {
        super(message);
    }
}
