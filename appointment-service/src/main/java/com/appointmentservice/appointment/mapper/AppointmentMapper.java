package com.appointmentservice.appointment.mapper;

import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.model.Appointment;

public class AppointmentMapper {
    public static AppointmentResponseDTO toDto(Appointment appointment) {
        AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();

        appointmentResponseDTO.setAppointmentId(appointment.getAppointmentId());
        appointmentResponseDTO.setPatientId(appointment.getPatientId());
        appointmentResponseDTO.setDoctorId(appointment.getDoctorId());
        appointmentResponseDTO.setAppointmentTime(appointment.getAppointmentTime());
        appointmentResponseDTO.setAppointmentStatus(appointment.getStatus());
        appointmentResponseDTO.setCreatedAt(appointment.getCreatedAt());
        appointmentResponseDTO.setUpdatedAt(appointment.getUpdatedAt());

        return appointmentResponseDTO;
    }

    public static Appointment toModel(AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = new Appointment();

        appointment.setDoctorId(appointmentRequestDTO.getDoctorId());
        appointment.setPatientId(appointmentRequestDTO.getPatientId());
        appointment.setSlotId(appointmentRequestDTO.getSlotId());
        appointment.setAppointmentTime(appointmentRequestDTO.getAppointmenTime());
        appointment.setStatus(appointmentRequestDTO.getAppointmentStatus());
        appointment.setRank(appointmentRequestDTO.getRank());

        return appointment;
    }
}
