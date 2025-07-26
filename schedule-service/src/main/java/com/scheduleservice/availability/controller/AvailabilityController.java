package com.scheduleservice.availability.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scheduleservice.availability.dto.AvailabilityCreateRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityCreateResponseDTO;
import com.scheduleservice.availability.dto.AvailabilityPatchDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.service.AvailabilitiyService;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {

    private final AvailabilitiyService availabilitiyService;

    public AvailabilityController(AvailabilitiyService availabilitiyService) {
        this.availabilitiyService = availabilitiyService;
    }

    @PostMapping
    public ResponseEntity<AvailabilityCreateResponseDTO> createAvailability(
            @RequestBody @Valid AvailabilityCreateRequestDTO availabilityCreateRequestDTO) {
        AvailabilityCreateResponseDTO availabilityCreateResponseDTO = availabilitiyService
                .createAvailability(availabilityCreateRequestDTO);

        return ResponseEntity.ok().body(availabilityCreateResponseDTO);
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

    @GetMapping("/availability-id")
    public ResponseEntity<UUID> getAvailabilityId(
            @RequestParam(name = "doctorId") UUID doctorId,
            @RequestParam(name = "slotId") UUID slotId,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        UUID availabilityId = availabilitiyService.getAvailibilityId(doctorId, slotId, date);
        return ResponseEntity.ok(availabilityId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDTO> updateAvailability(@PathVariable("id") UUID availabilityId,
            @Validated({ Default.class }) @RequestBody AvailabilityPatchDTO availabilityPatchDTO) {
        AvailabilityResponseDTO availabilityResponseDTO = availabilitiyService.updateAvailability(availabilityId,
                availabilityPatchDTO);
        return ResponseEntity.ok().body(availabilityResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAvailability(@PathVariable("id") UUID availabilityId) {
        availabilitiyService.deleteAvailability(availabilityId);
        return ResponseEntity.ok("Availability deleted successfully");
    }
}
