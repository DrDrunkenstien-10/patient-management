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
import com.scheduleservice.schedule.exception.ScheduleNotFoundException;
import com.scheduleservice.schedule.model.Schedule;
import com.scheduleservice.schedule.repository.ScheduleRepository;
import com.scheduleservice.slot.dto.SlotResponseDTO;
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

    public void createAvailability(SlotResponseDTO slotResponseDTO) {
        UUID doctorId = slotResponseDTO.getDoctorId();
        UUID slotId = slotResponseDTO.getSlotId();

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + slotId));

        List<Schedule> schedules = scheduleRepository.findByDocId(doctorId);

        List<Availability> availabilitiesToSave = new ArrayList<>();

        for (Schedule schedule : schedules) {
            LocalDate start = schedule.getStartDate();
            LocalDate end = schedule.getEndDate();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                Availability availability = new Availability();

                availability.setDocId(doctorId);
                availability.setSlot(slot);
                availability.setSchedule(schedule); // ✅ set the schedule
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

        Schedule schedule = scheduleRepository.findById(availabilityRequestDTO.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException(
                        "Schedule not found with id: " + availabilityRequestDTO.getScheduleId()));

        Slot slot = slotRepository.findById(availabilityRequestDTO.getSlotId())
                .orElseThrow(() -> new SlotNotFoundException(
                        "Slot not found with id: " + availabilityRequestDTO.getSlotId()));

        availability.setDocId(availabilityRequestDTO.getDocId());
        availability.setSlot(slot);
        availability.setSchedule(schedule);
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
