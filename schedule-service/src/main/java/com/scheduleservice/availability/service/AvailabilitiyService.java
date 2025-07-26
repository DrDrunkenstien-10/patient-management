package com.scheduleservice.availability.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scheduleservice.availability.dto.AvailabilityCreateRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityCreateResponseDTO;
import com.scheduleservice.availability.dto.AvailabilityPatchDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.exception.AvailibilityExceptionHandler;
import com.scheduleservice.availability.mapper.AvailabilityMapper;
import com.scheduleservice.availability.model.Availability;
import com.scheduleservice.availability.repository.AvailabiltyRepository;
import com.scheduleservice.schedule.model.Schedule;
import com.scheduleservice.schedule.repository.ScheduleRepository;
import com.scheduleservice.slot.exception.SlotNotFoundException;
import com.scheduleservice.slot.model.Slot;
import com.scheduleservice.slot.repository.SlotRepository;

import jakarta.transaction.Transactional;

@Service
public class AvailabilitiyService {

    private final ScheduleRepository scheduleRepository;
    private final AvailabiltyRepository availabiltyRepository;
    private final SlotRepository slotRepository;

    public AvailabilitiyService(ScheduleRepository scheduleRepository,
            AvailabiltyRepository availabiltyRepository,
            SlotRepository slotRepository) {
        this.scheduleRepository = scheduleRepository;
        this.availabiltyRepository = availabiltyRepository;
        this.slotRepository = slotRepository;
    }

    public AvailabilityCreateResponseDTO createAvailability(AvailabilityCreateRequestDTO dto) {
        UUID doctorId = dto.getDoctorId();

        // 1. Create the Schedule
        Schedule newSchedule = new Schedule();

        newSchedule.setDocId(dto.getDoctorId());
        newSchedule.setStartDate(dto.getStartDate());
        newSchedule.setEndDate(dto.getEndDate());
        newSchedule.setScheduleType(dto.getScheduleType());
        newSchedule.setCreatedAt(OffsetDateTime.now());
        newSchedule.setUpdatedAt(OffsetDateTime.now());

        newSchedule = scheduleRepository.save(newSchedule);

        // 2. Get the Slots
        List<Slot> slots = slotRepository.findAllById(dto.getSlotIds());
        if (slots.size() != dto.getSlotIds().size()) {
            throw new SlotNotFoundException("One or more slot IDs are invalid");
        }

        List<Availability> availabilitiesToSave = new ArrayList<>();

        // 3. For each date in the range, create Availability for each slot
        for (LocalDate date = dto.getStartDate(); !date.isAfter(dto.getEndDate()); date = date.plusDays(1)) {
            for (Slot slot : slots) {
                Availability availability = new Availability();

                availability.setDocId(doctorId);
                availability.setSchedule(newSchedule);
                availability.setSlot(slot);
                availability.setDate(date);
                availability.setAvailability(true);
                availability.setUnavailabilityReason(null);
                OffsetDateTime now = OffsetDateTime.now();
                availability.setCreatedAt(now);
                availability.setUpdatedAt(now);

                availabilitiesToSave.add(availability);
            }
        }

        availabiltyRepository.saveAll(availabilitiesToSave);

        AvailabilityCreateResponseDTO availabilityCreateResponseDTO = new AvailabilityCreateResponseDTO();

        availabilityCreateResponseDTO.setDoctorId(doctorId);
        availabilityCreateResponseDTO.setScheduleId(newSchedule.getScheduleId());
        availabilityCreateResponseDTO.setScheduleType(dto.getScheduleType());
        availabilityCreateResponseDTO.setSlotIds(dto.getSlotIds());
        availabilityCreateResponseDTO.setStartDate(dto.getStartDate());
        availabilityCreateResponseDTO.setEndDate(dto.getEndDate());

        return availabilityCreateResponseDTO;
    }

    public List<AvailabilityResponseDTO> getAvailabilities() {
        List<Availability> availabilities = availabiltyRepository.findAll();

        List<AvailabilityResponseDTO> availabilityResponseDTO = availabilities.stream()
                .map(availibility -> AvailabilityMapper.toDto(availibility))
                .toList();

        return availabilityResponseDTO;
    }

    public AvailabilityResponseDTO getAvailibilityById(UUID availabilityId) {
        Availability availability = availabiltyRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availibility not found with ID: " + availabilityId));
        return AvailabilityMapper.toDto(availability);
    }

    public UUID getAvailibilityId(UUID doctorId, UUID slotId, LocalDate date) {
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Slot ID: " + slotId);
        System.out.println("Date: " + date);

        return availabiltyRepository.findIdByDocIdAndSlotIdAndDate(
                doctorId, slotId, date).orElseThrow(
                        () -> new AvailibilityExceptionHandler(
                                "Availibility ID not found."));
    }

    public AvailabilityResponseDTO updateAvailability(UUID availabilityId,
            AvailabilityPatchDTO patchDTO) {
        Availability availability = availabiltyRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));

        if (patchDTO.getAvailability() != null) {
            availability.setAvailability(patchDTO.getAvailability());
        }

        if (patchDTO.getUnavailabilityReason() != null) {
            availability.setUnavailabilityReason(patchDTO.getUnavailabilityReason());
        }

        Availability updatedAvailability = availabiltyRepository.save(availability);
        return AvailabilityMapper.toDto(updatedAvailability);
    }

    @Transactional
    public void deleteAvailability(UUID availabilityId) {
        Availability availability = availabiltyRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));
        availabiltyRepository.delete(availability);
    }
}
