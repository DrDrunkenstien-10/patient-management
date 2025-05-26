package com.scheduleservice.availability.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scheduleservice.availability.dto.AvailabilityRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.exception.AvailibilityExceptionHandler;
import com.scheduleservice.availability.mapper.AvailabilityMapper;
import com.scheduleservice.availability.model.Availability;
import com.scheduleservice.availability.repository.AvailabiltyRepository;

import jakarta.transaction.Transactional;

@Service
public class AvailabilitiyService {

    private AvailabiltyRepository availabilityRepository;

    public AvailabilitiyService(AvailabiltyRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;

    }

    public List<AvailabilityResponseDTO> getAvailabilities() {
        List<Availability> availabilities = availabilityRepository.findAll();

        List<AvailabilityResponseDTO> availabilityResponseDTO = availabilities.stream()
                .map(availibility -> AvailabilityMapper.toDto(availibility))
                .toList();

        return availabilityResponseDTO;
    }

    public AvailabilityResponseDTO getAvailibilityById(UUID availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availibility not found with ID: " + availabilityId));
        return AvailabilityMapper.toDto(availability);
    }

    public AvailabilityResponseDTO updateAvailability(UUID availabilityId,
            AvailabilityRequestDTO availabilityRequestDTO) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));

        availability.setDocId(availabilityRequestDTO.getDocId());
        availability.setSlotId(availabilityRequestDTO.getSlotId());
        availability.setDate(availabilityRequestDTO.getDate());
        availability.setAvailability(availabilityRequestDTO.getAvailability());
        availability.setUnavailabilityReason(availabilityRequestDTO.getUnavailabilityReason());

        Availability updatedAvailability = availabilityRepository.save(availability);

        return AvailabilityMapper.toDto(updatedAvailability);
    }

    @Transactional
    public void deleteAvailability(UUID availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailibilityExceptionHandler(
                        "Availability not found with ID: " + availabilityId));
        availabilityRepository.delete(availability);
    }

}
