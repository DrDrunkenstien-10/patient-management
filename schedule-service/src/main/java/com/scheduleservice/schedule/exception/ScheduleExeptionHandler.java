package com.scheduleservice.schedule.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ScheduleExeptionHandler extends RuntimeException {
    public ScheduleExeptionHandler(String message) {
        super(message);
    }
}
