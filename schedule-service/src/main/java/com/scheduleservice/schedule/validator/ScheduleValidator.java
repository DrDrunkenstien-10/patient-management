package com.scheduleservice.schedule.validator;

import org.springframework.stereotype.Component;

import com.scheduleservice.client.service.DoctorServiceClient;
import com.scheduleservice.schedule.dto.ScheduleRequestDTO;
import com.scheduleservice.schedule.exception.DoctorIdAlreadyExistsException;
import com.scheduleservice.schedule.repository.ScheduleRepository;
import com.scheduleservice.slot.exception.DoctorNotFoundException;

@Component
public class ScheduleValidator {
    private final ScheduleRepository scheduleRepository;
    private final DoctorServiceClient doctorServiceClient;

    public ScheduleValidator(ScheduleRepository scheduleRepository, DoctorServiceClient doctorServiceClient) {
        this.scheduleRepository = scheduleRepository;
        this.doctorServiceClient = doctorServiceClient;
    }

    public void validateForCreation(ScheduleRequestDTO scheduleRequestDTO) {

        if (scheduleRepository.existsByDocId(scheduleRequestDTO.getDocId())) {
            throw new DoctorIdAlreadyExistsException(
                    "Doctor with same ID already exists: " + scheduleRequestDTO.getDocId());
        }

        if (!doctorServiceClient.isDoctorExists(scheduleRequestDTO.getDocId())) {
            throw new DoctorNotFoundException("Doctor not found");
        }
    }
}
