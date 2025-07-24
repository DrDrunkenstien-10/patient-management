package com.scheduleservice.availability.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.scheduleservice.schedule.enums.ScheduleType;

public class AvailabilityCreateResponseDTO {
    private UUID doctorId;
    private UUID scheduleId;
    private ScheduleType scheduleType;
    private List<UUID> slotIds;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and setters
    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public List<UUID> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<UUID> slotIds) {
        this.slotIds = slotIds;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
