package com.scheduleservice.slot.exception;

public class DuplicateSlotException extends RuntimeException {
    public DuplicateSlotException(String message) {
        super(message);
    }
}
