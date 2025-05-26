package com.scheduleservice.schedule.validator;

import org.springframework.stereotype.Component;

import com.scheduleservice.schedule.dto.ScheduleRequestDTO;
import com.scheduleservice.schedule.exception.DoctorIdAlreadyExistsException;
import com.scheduleservice.schedule.repository.ScheduleRepository;

@Component
public class ScheduleValidator {
    private final ScheduleRepository scheduleRepository;

    public ScheduleValidator(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void validateForCreation(ScheduleRequestDTO scheduleRequestDTO) {
        if (scheduleRepository.existsByDocId(scheduleRequestDTO.getDocId())) {
            throw new DoctorIdAlreadyExistsException(
                    "Doctor with same ID already exists: " + scheduleRequestDTO.getDocId());
        }
    }
}
