package com.scheduleservice.slot.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.scheduleservice.slot.dto.SlotRequestDTO;

@Service
public class CapacityCalculator {

    public int calculateCapacity(SlotRequestDTO slotRequestDTO) {
        if (slotRequestDTO.getStartTime() == null || slotRequestDTO.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time must not be null.");
        }

        if (slotRequestDTO.getSessionDuration() <= 0) {
            throw new IllegalArgumentException("Session duration must be a positive integer.");
        }

        long durationInMinutes = Duration.between(slotRequestDTO.getStartTime(), slotRequestDTO.getEndTime())
                .toMinutes();

        if (durationInMinutes <= 0) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        return (int) (durationInMinutes / slotRequestDTO.getSessionDuration());
    }
}
