package com.scheduleservice.availability.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scheduleservice.availability.dto.AvailabilityRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.service.AvailabilitiyService;

import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilitiyService availabilitiyService;

    public AvailabilityController(AvailabilitiyService availabilitiyService) {
        this.availabilitiyService = availabilitiyService;
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityResponseDTO>> getAvailabilities() {
        List<AvailabilityResponseDTO> availabilities = availabilitiyService.getAvailabilities();
        return ResponseEntity.ok().body(availabilities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDTO> getAvailabilityById(@PathVariable("id") UUID availabilityId) {
        AvailabilityResponseDTO availabilityResponseDTO = availabilitiyService.getAvailibilityById(availabilityId);
        return ResponseEntity.ok().body(availabilityResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDTO> updateAvailability(@PathVariable("id") UUID availabilityId,
            @Validated({ Default.class }) @RequestBody AvailabilityRequestDTO availabilityRequestDTO) {
        AvailabilityResponseDTO availabilityResponseDTO = availabilitiyService.updateAvailability(availabilityId,
                availabilityRequestDTO);
        return ResponseEntity.ok().body(availabilityResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAvailability(@PathVariable("id") UUID availabilityId) {
        availabilitiyService.deleteAvailability(availabilityId);
        return ResponseEntity.ok("Availability deleted successfully");
    }

}
