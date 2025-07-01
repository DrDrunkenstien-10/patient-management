package com.appointmentservice.appointment.dto;

import com.appointmentservice.appointment.enums.AppointmentStatus;

public class AppointmentStatusUpdateDTO {
    private AppointmentStatus status;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    
}
