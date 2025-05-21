package com.adminservice.systemadmin.exception;

public class SystemAdminNotFoundException extends RuntimeException {
    public SystemAdminNotFoundException(String message) {
        super(message);
    }
}
