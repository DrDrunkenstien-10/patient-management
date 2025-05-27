package com.appointmentservice.appointment.dto;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.appointmentservice.appointment.enums.AppointmentStatus;

public class AppointmentResponseDTO {
    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private UUID slotId;
    private LocalTime appointmentTime;
    private AppointmentStatus appointmentStatus;
    private String slotName;
    private String doctorName;
    private String patientName;
    private int rank;
    private String bookedAppointmentTime;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    // Getters and setters
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

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getBookedAppointmentTime() {
        return bookedAppointmentTime;
    }

    public void setBookedAppointmentTime(String bookedAppointmentTime) {
        this.bookedAppointmentTime = bookedAppointmentTime;
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
}
