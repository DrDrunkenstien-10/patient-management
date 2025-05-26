package com.scheduleservice.slot.mapper;

import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.dto.SlotResponseDTO;
import com.scheduleservice.slot.model.Slot;

public interface SlotMapper {

    public static SlotResponseDTO toDto(Slot slot) {
        SlotResponseDTO dto = new SlotResponseDTO();
        dto.setSlotId(slot.getSlotId());
        dto.setName(slot.getName());
        dto.setStartTime(slot.getStartTime().toString());
        dto.setEndTime(slot.getEndTime().toString());
        dto.setCapacity(slot.getCapacity());
        dto.setSessionDuration(slot.getSessionDuration());
        dto.setDoctorId(slot.getDoctorId());
        return dto;
    }

    public static Slot toModel(SlotRequestDTO dto) {

        Slot slot = new Slot();
        slot.setName(dto.getName());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setCapacity(dto.getCapacity());
        slot.setSessionDuration(dto.getSessionDuration());
        slot.setDoctorId(dto.getDoctorId());
        return slot;
    }

}