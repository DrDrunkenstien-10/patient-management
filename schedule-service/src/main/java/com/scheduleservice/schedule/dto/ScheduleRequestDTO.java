package com.scheduleservice.schedule.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.scheduleservice.schedule.enums.ScheduleType;

import jakarta.validation.constraints.NotNull;

public class ScheduleRequestDTO {

    private UUID scheduleId;

    @NotNull
    private UUID docId;

    @NotNull
    private ScheduleType scheduleType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    //Getters and Setters

    public UUID getScheduleId() {
        return scheduleId;
    }   

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public UUID getDocId() {
        return docId;
    }

    public void setDocId(UUID docId) {
        this.docId = docId;
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
    
    // Getters and Setters
    
}