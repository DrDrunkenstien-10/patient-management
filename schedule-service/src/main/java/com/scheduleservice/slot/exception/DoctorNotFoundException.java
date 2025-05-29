package com.scheduleservice.slot.exception;

public class DoctorNotFoundException extends  RuntimeException {
    
    public DoctorNotFoundException(String message){
        super(message);
    }
}
