package com.scheduleservice.slot.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scheduleservice.slot.dto.PaginatedResponseDTO;
import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.dto.SlotResponseDTO;
import com.scheduleservice.slot.exception.SlotNotFoundException;
import com.scheduleservice.slot.mapper.SlotMapper;
import com.scheduleservice.slot.model.Slot;
import com.scheduleservice.slot.repository.SlotRepository;
import com.scheduleservice.slot.specification.SlotSpecification;
import com.scheduleservice.slot.validator.SlotValidator;

import jakarta.transaction.Transactional;

@Service
public class SlotService {
    private final CapacityCalculator capacityCalculator;
    private final SlotRepository slotRepository;
    private final SlotValidator slotValidator;

    public SlotService(CapacityCalculator capacityCalculator, SlotRepository slotRepository,
            SlotValidator slotValidator) {
        this.capacityCalculator = capacityCalculator;
        this.slotRepository = slotRepository;
        this.slotValidator = slotValidator;
    }

    public SlotResponseDTO createSlot(SlotRequestDTO slotRequestDTO) {

        slotValidator.validateForCreation(slotRequestDTO);

        int capacity = capacityCalculator.calculateCapacity(slotRequestDTO);
        slotRequestDTO.setCapacity(capacity);

        Slot newSlot = slotRepository.save(SlotMapper.toModel(slotRequestDTO));

        SlotResponseDTO slotResponseDTO = SlotMapper.toDto(newSlot);

        return slotResponseDTO;
    }

    public PaginatedResponseDTO<SlotResponseDTO> getSlots(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Slot> slots = slotRepository.findAll(pageable);

        List<SlotResponseDTO> slotResponseDTOs = slots.stream()
                .map(slot -> SlotMapper.toDto(slot))
                .toList();

        return new PaginatedResponseDTO<>(
                slotResponseDTOs,
                slots.getNumber(),
                slots.getSize(),
                slots.getTotalElements(),
                slots.getTotalPages(),
                slots.isLast(),
                slots.isFirst());
    }

    public PaginatedResponseDTO<SlotResponseDTO> filterSlots(
            String category,
            String value,
            String direction,
            int page,
            int size,
            String sortBy) {

        slotValidator.validateFilterCategory(category);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Slot> spec = SlotSpecification.getSlotSpecification(category, value);

        Page<Slot> slots = slotRepository.findAll(spec, pageable);

        List<SlotResponseDTO> slotResponseDTOs = slots.stream().map(slot -> SlotMapper.toDto(slot))
                .toList();

        return new PaginatedResponseDTO<>(
                slotResponseDTOs,
                slots.getNumber(),
                slots.getSize(),
                slots.getTotalElements(),
                slots.getTotalPages(),
                slots.isLast(),
                slots.isFirst());
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

    public boolean isSlotExists(UUID slotId) {
        return slotRepository.existsById(slotId);
    }
}