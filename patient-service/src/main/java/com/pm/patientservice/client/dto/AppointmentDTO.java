package com.pm.patientservice.client.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.pm.patientservice.client.enums.AppointmentStatus;

public class AppointmentDTO {
    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private UUID slotId;
    private AppointmentStatus appointmentStatus;

    private String appointmentTime;  // e.g., "11:00-11:30 AM"
    private String appointmentDate;  // e.g., "2025/05/27"
    private String slotName;         // e.g., "Morning"
    private String doctorName;       // e.g., "Dr. A"
    private String patientName;      // e.g., "Patient B"
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


    public UUID getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
    public UUID getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public UUID getSlotId() {
        return slotId;
    }
    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }
    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public String getAppointmentDate() {
        return appointmentDate;
    }
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    public String getSlotName() {
        return slotName;
    }
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    

}
