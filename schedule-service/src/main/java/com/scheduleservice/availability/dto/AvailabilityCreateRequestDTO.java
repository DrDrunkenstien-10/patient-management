package com.scheduleservice.availability.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.scheduleservice.schedule.enums.ScheduleType;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AvailabilityCreateRequestDTO {
    @NotNull
    private UUID doctorId;

    @NotEmpty
    private List<UUID> slotIds;

    @NonNull
    private ScheduleType scheduleType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    // Getters and setters
    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public List<UUID> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<UUID> slotIds) {
        this.slotIds = slotIds;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
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
