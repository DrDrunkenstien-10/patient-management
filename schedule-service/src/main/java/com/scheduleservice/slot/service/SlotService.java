package com.scheduleservice.slot.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scheduleservice.availability.service.AvailabilitiyService;
import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.dto.SlotResponseDTO;
import com.scheduleservice.slot.exception.SlotNotFoundException;
import com.scheduleservice.slot.mapper.SlotMapper;
import com.scheduleservice.slot.model.Slot;
import com.scheduleservice.slot.repository.SlotRepository;

import jakarta.transaction.Transactional;

@Service
public class SlotService {
    private final CapacityCalculator capacityCalculator;
    private final SlotRepository slotRepository;
    private final AvailabilitiyService availabilitiyService;

    public SlotService(CapacityCalculator capacityCalculator, SlotRepository slotRepository,
            AvailabilitiyService availabilitiyService) {
        this.capacityCalculator = capacityCalculator;
        this.slotRepository = slotRepository;
        this.availabilitiyService = availabilitiyService;
    }

    public SlotResponseDTO createSlot(SlotRequestDTO slotRequestDTO) {
        int capacity = capacityCalculator.calculateCapacity(slotRequestDTO);
        slotRequestDTO.setCapacity(capacity);

        Slot newSlot = slotRepository.save(SlotMapper.toModel(slotRequestDTO));

        SlotResponseDTO slotResponseDTO = SlotMapper.toDto(newSlot);

        availabilitiyService.createAvailability(slotResponseDTO);

        return slotResponseDTO;
    }

    public List<SlotResponseDTO> getSlots() {
        List<Slot> slots = slotRepository.findAll();

        List<SlotResponseDTO> slotResponseDTOs = slots.stream()
                .map(slot -> SlotMapper.toDto(slot))
                .toList();

        return slotResponseDTOs;
    }

    public SlotResponseDTO getSlotById(UUID slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(
                        "Slot not found with ID: " + slotId));
        return SlotMapper.toDto(slot);
    }

    public SlotResponseDTO updateSlot(UUID slotId, SlotRequestDTO slotRequestDTO) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(
                        "Slot not found with ID: " + slotId));

        slot.setName(slotRequestDTO.getName());
        slot.setStartTime(slotRequestDTO.getEndTime());
        slot.setEndTime(slotRequestDTO.getEndTime());
        slot.setCapacity(slotRequestDTO.getCapacity());
        slot.setSessionDuration(slotRequestDTO.getSessionDuration());
        slot.setDoctorId(slotRequestDTO.getDoctorId());

        Slot updatedSlot = slotRepository.save(slot);

        return SlotMapper.toDto(updatedSlot);
    }

    @Transactional
    public void deleteSlot(UUID slotId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(
                        "Slot not found with ID: " + slotId));
        slotRepository.delete(slot);
    }
}