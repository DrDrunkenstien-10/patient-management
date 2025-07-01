package com.appointmentservice.appointment.mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.appointmentservice.appointment.client.dto.DoctorDTO;
import com.appointmentservice.appointment.client.dto.PatientDTO;
import com.appointmentservice.appointment.client.dto.SlotDTO;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.model.Appointment;

public class AppointmentMapper {
    public static AppointmentResponseDTO toDto(
            Appointment appointment,
            SlotDTO slot,
            DoctorDTO doctor,
            PatientDTO patient) {

        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        dto.setSlotId(appointment.getSlotId());
        dto.setAppointmentStatus(appointment.getStatus());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Get start time from appointment
        LocalTime startTime = appointment.getAppointmentTime();
        LocalTime endTime = startTime.plusMinutes(slot.getSessionDuration()); // add session duration

        dto.setAppointmentTime(startTime.format(timeFormatter) + " - " + endTime.format(timeFormatter));
        dto.setAppointmentDate(appointment.getAppointmentDate().format(dateFormatter));

        dto.setSlotName(slot.getName());
        dto.setDoctorName(doctor.getName());
        dto.setPatientName(patient.getName());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());

        return dto;
    }

    public static AppointmentResponseDTO toDto(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        dto.setSlotId(appointment.getSlotId());
        dto.setAppointmentStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());

        return dto;
    }

    public static Appointment toModel(AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = new Appointment();

        appointment.setDoctorId(appointmentRequestDTO.getDoctorId());
        appointment.setPatientId(appointmentRequestDTO.getPatientId());
        appointment.setSlotId(appointmentRequestDTO.getSlotId());
        appointment.setAppointmentTime(appointmentRequestDTO.getAppointmenTime());
        appointment.setAppointmentDate(appointmentRequestDTO.getAppointmentDate());
        appointment.setStatus(appointmentRequestDTO.getAppointmentStatus());
        appointment.setRank(appointmentRequestDTO.getRank());

        return appointment;
    }
}
