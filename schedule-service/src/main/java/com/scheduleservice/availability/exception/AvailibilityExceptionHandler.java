package com.scheduleservice.availability.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class AvailibilityExceptionHandler  extends RuntimeException{
    


public AvailibilityExceptionHandler(String message) {
        super(message);
    }

}
