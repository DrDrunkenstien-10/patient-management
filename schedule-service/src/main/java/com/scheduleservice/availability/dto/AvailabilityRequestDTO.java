package com.scheduleservice.availability.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

public class AvailabilityRequestDTO {

    private UUID availabilityId;

    @NotNull
    private UUID docId;

    @NotNull
    private UUID slotId;

    @NotNull
    private UUID scheduleId;

    @NotNull
    private LocalDate date;

    @NotNull
    private Boolean availability;

    // Optional, but if present should not be blank
    private String unavailabilityReason;

    // Getters and Setters

    public UUID getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(UUID availabilityId) {
        this.availabilityId = availabilityId;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
    }

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getUnavailabilityReason() {
        return unavailabilityReason;
    }

    public void setUnavailabilityReason(String unavailabilityReason) {
        this.unavailabilityReason = unavailabilityReason;
    }
}
