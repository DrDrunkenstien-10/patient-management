package com.scheduleservice.slot.validator;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.scheduleservice.client.service.DoctorServiceClient;
import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.exception.DoctorNotFoundException;
import com.scheduleservice.slot.exception.DuplicateSlotException;
import com.scheduleservice.slot.exception.InvalidFilterCategoryException;
import com.scheduleservice.slot.repository.SlotRepository;

@Component
public class SlotValidator {

    private static final Set<String> ALLOWED_FILTER_CATEGORIES = Set.of(
            "name", "start_time", "end_time", "capacity", "session_duration",
            "slot_id", "doctor_id");

    private final DoctorServiceClient doctorServiceClient;
    private final SlotRepository slotRepository;

    public SlotValidator(DoctorServiceClient doctorServiceClient, SlotRepository slotRepository) {
        this.doctorServiceClient = doctorServiceClient;
        this.slotRepository = slotRepository;
    }

    public void validateForCreation(SlotRequestDTO slotRequestDTO) {
        if (!doctorServiceClient.isDoctorExists(slotRequestDTO.getDoctorId())) {
            throw new DoctorNotFoundException("Doctor not found");
        }

        // Check duplicate slot name for the same doctor
        boolean nameExists = slotRepository.existsByDoctorIdAndName(slotRequestDTO.getDoctorId(),
                slotRequestDTO.getName());

        // Check duplicate slot start and end time for the same doctor
        boolean timeExists = slotRepository.existsByDoctorIdAndStartTimeAndEndTime(
                slotRequestDTO.getDoctorId(),
                slotRequestDTO.getStartTime(),
                slotRequestDTO.getEndTime());

        if (nameExists || timeExists) {
            throw new DuplicateSlotException(
                    "A slot with the same name or start/end time already exists for this doctor.");
        }
    }

    public void validateFilterCategory(String category) {
        if (!ALLOWED_FILTER_CATEGORIES.contains(category.toLowerCase())) {
            throw new InvalidFilterCategoryException("Unsupported filter category: " + category);
        }
    }
}
