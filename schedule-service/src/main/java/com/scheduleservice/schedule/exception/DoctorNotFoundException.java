package com.scheduleservice.schedule.exception;

public class DoctorNotFoundException extends  RuntimeException {
    
    public DoctorNotFoundException(String message){
        super(message);
    }
}
