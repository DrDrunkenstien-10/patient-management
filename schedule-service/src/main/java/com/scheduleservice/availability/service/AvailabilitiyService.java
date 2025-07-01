package com.scheduleservice.availability.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scheduleservice.availability.dto.AvailabilityRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.exception.AvailibilityExceptionHandler;
import com.scheduleservice.availability.mapper.AvailabilityMapper;
import com.scheduleservice.availability.model.Availability;
import com.scheduleservice.availability.repository.AvailabiltyRepository;
import com.scheduleservice.schedule.repository.ScheduleDateRange;
import com.scheduleservice.schedule.repository.ScheduleRepository;
import com.scheduleservice.slot.dto.SlotResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class AvailabilitiyService {

    private final ScheduleRepository scheduleRepository;
    private final AvailabiltyRepository availabiltyRepository;

    public AvailabilitiyService(ScheduleRepository scheduleRepository, AvailabiltyRepository availabiltyRepository) {
        this.scheduleRepository = scheduleRepository;
        this.availabiltyRepository = availabiltyRepository;
    }

    public void createAvailability(SlotResponseDTO slotResponseDTO) {
        List<ScheduleDateRange> scheduleDateRanges = scheduleRepository
                .findByDocId(slotResponseDTO.getDoctorId());

        List<Availability> availabilitiesToSave = new ArrayList<>();

        for (ScheduleDateRange dateRange : scheduleDateRanges) {
            LocalDate start = dateRange.getStartDate();
            LocalDate end = dateRange.getEndDate();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                Availability availability = new Availability();

                availability.setDocId(slotResponseDTO.getDoctorId());
                availability.setSlotId(slotResponseDTO.getSlotId());
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

    public AvailabilityResponseDTO updateAvailability(UUID availabilityId,
            AvailabilityRequestDTO availabilityRequestDTO) {
        Availability availability = availabiltyRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));

        availability.setDocId(availabilityRequestDTO.getDocId());
        availability.setSlotId(availabilityRequestDTO.getSlotId());
        availability.setDate(availabilityRequestDTO.getDate());
        availability.setAvailability(availabilityRequestDTO.getAvailability());
        availability.setUnavailabilityReason(availabilityRequestDTO.getUnavailabilityReason());

        Availability updatedAvailability = availabiltyRepository.save(availability);

        return AvailabilityMapper.toDto(updatedAvailability);
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

    @Transactional
    public void deleteAvailability(UUID availabilityId) {
        Availability availability = availabiltyRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));
        availabiltyRepository.delete(availability);
    }
}
