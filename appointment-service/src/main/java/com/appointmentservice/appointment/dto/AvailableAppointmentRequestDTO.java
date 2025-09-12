package com.appointmentservice.appointment.dto;

import java.time.LocalDate;
import java.util.UUID;

public class AvailableAppointmentRequestDTO {
    private UUID doctorId;
    private LocalDate date;
    private UUID slotId;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }

}
