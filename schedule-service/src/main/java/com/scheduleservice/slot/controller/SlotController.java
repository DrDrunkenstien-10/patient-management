package com.scheduleservice.slot.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scheduleservice.slot.dto.PaginatedResponseDTO;
import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.dto.SlotResponseDTO;
import com.scheduleservice.slot.service.SlotService;

import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/slots")
public class SlotController {
    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<SlotResponseDTO>> getSlots(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

        PaginatedResponseDTO<SlotResponseDTO> slots = slotService.getSlots(page, size, sortBy);

        return ResponseEntity.ok().body(slots);
    }

    @GetMapping("/filter")
    public ResponseEntity<PaginatedResponseDTO<SlotResponseDTO>> filterSlot(
            @RequestParam(name = "category", defaultValue = "name", required = true) String category,
            @RequestParam(name = "value", defaultValue = "", required = false) String value,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

        PaginatedResponseDTO<SlotResponseDTO> slots = slotService
                .filterSlots(category, value, direction, page, size, sortBy);

        return ResponseEntity.ok().body(slots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlotResponseDTO> getSlotById(@PathVariable("id") UUID slotId) {
        SlotResponseDTO slotResponseDTO = slotService.getSlotById(slotId);
        return ResponseEntity.ok().body(slotResponseDTO);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> isSlotExists(@PathVariable("id") UUID slotId) {
        boolean exists = slotService.isSlotExists(slotId);
        return ResponseEntity.ok().body(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotResponseDTO> updateSlot(@PathVariable("id") UUID slotId,
            @Validated({ Default.class }) @RequestBody SlotRequestDTO slotRequestDTO) {
        SlotResponseDTO slotResponseDTO = slotService.updateSlot(slotId, slotRequestDTO);
        return ResponseEntity.ok().body(slotResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable("id") UUID slotId) {
        slotService.deleteSlot(slotId);
        return ResponseEntity.ok("Slot deleted successfully");
    }

    @PostMapping
    public ResponseEntity<SlotResponseDTO> createSlot(@RequestBody SlotRequestDTO slotRequestDTO) {
        SlotResponseDTO slotResponseDTO = slotService.createSlot(slotRequestDTO);
        return ResponseEntity.ok().body(slotResponseDTO);
    }
}
