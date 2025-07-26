package com.scheduleservice.availability.dto;

public class AvailabilityPatchDTO {

    private Boolean availability;

    private String unavailabilityReason;

    // Getters and Setters
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
