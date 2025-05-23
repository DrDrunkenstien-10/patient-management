package com.scheduleservice.availability.mapper;

import com.scheduleservice.availability.dto.AvailabilityRequestDTO;
import com.scheduleservice.availability.dto.AvailabilityResponseDTO;
import com.scheduleservice.availability.model.Availability;

public class AvailabilityMapper {

    public static AvailabilityResponseDTO toDto(Availability availability) {
        if (availability == null) {
            return null;
        }
        AvailabilityResponseDTO dto = new AvailabilityResponseDTO();
        dto.setAvailabilityId(availability.getId().toString());
        dto.setDocId(availability.getDocId().toString());
        dto.setSlotId(availability.getSlotId().toString());
        dto.setDate(availability.getDate().toString());
        dto.setAvailability(availability.getAvailability());
        dto.setUnavailabilityReason(availability.getUnavailabilityReason());
        return dto;
    }

    
    public static Availability toModel(AvailabilityRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Availability availability = new Availability();
        availability.setId(dto.getAvailabilityId());
        availability.setDocId(dto.getDocId());
        availability.setSlotId(dto.getSlotId());
        availability.setDate(dto.getDate());
        availability.setAvailability(dto.getAvailability());
        availability.setUnavailabilityReason(dto.getUnavailabilityReason());
        return availability;
    }
}