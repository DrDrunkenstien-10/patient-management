package com.scheduleservice.slot.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<SlotResponseDTO>> getSlots() {
        List<SlotResponseDTO> slots = slotService.getSlots();
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
