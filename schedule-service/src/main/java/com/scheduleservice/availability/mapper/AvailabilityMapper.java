package com.scheduleservice.availability.mapper;

import com.scheduleservice.availability.dto.AvailabilityRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.model.Availability;
import com.scheduleservice.slot.model.Slot;

public class AvailabilityMapper {

    public static AvailabilityResponseDTO toDto(Availability availability) {
        if (availability == null) {
            return null;
        }
        AvailabilityResponseDTO dto = new AvailabilityResponseDTO();
        dto.setAvailabilityId(availability.getId().toString());
        dto.setDocId(availability.getDocId().toString());
        dto.setSlotId(availability.getSlot().getSlotId().toString());
        dto.setScheduleId(availability.getSchedule().getScheduleId().toString());
        dto.setDate(availability.getDate().toString());
        dto.setAvailability(availability.getAvailability());
        dto.setUnavailabilityReason(availability.getUnavailabilityReason());
        dto.setSlotName(availability.getSlot().getName());
        dto.setStartTime(availability.getSlot().getStartTime().toString());
        dto.setEndTime(availability.getSlot().getEndTime().toString());

        return dto;
    }

    public static Availability toModel(AvailabilityRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Availability availability = new Availability();
        availability.setId(dto.getAvailabilityId());
        availability.setDocId(dto.getDocId());

        Slot slot = new Slot();
        slot.setSlotId(dto.getSlotId());
        availability.setSlot(slot);

        availability.setDate(dto.getDate());
        availability.setAvailability(dto.getAvailability());
        availability.setUnavailabilityReason(dto.getUnavailabilityReason());

        return availability;
    }
}