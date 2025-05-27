package com.appointmentservice.appointment.service;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.mapper.AppointmentMapper;
import com.appointmentservice.appointment.model.Appointment;
import com.appointmentservice.appointment.repository.AppointmentRepository;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        Optional<Appointment> existingAppointmentOpt = appointmentRepository
                .findTop1ByDoctorIdAndSlotIdOrderByRankDesc(
                        appointmentRequestDTO.getDoctorId(),
                        appointmentRequestDTO.getSlotId());

        Appointment appointment;

        if (existingAppointmentOpt.isPresent()) {
            // Create subsequent appointment
            appointment = createSubsequentAppointment(existingAppointmentOpt.get(), appointmentRequestDTO);
        } else {
            // Create new appointment
            appointment = createFirstAppointment(appointmentRequestDTO);
        }

        return AppointmentMapper.toDto(appointment);
    }

    private Appointment createFirstAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        LocalTime startTime = LocalTime.parse("11:00:00");

        appointmentRequestDTO.setAppointmenTime(startTime);
        appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
        appointmentRequestDTO.setRank(1);

        return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
    }

    private Appointment createSubsequentAppointment(Appointment existingAppointment,
            AppointmentRequestDTO appointmentRequestDTO) {
        LocalTime appointmentTime = existingAppointment.getAppointmentTime();
        int rank = existingAppointment.getRank();
        System.out.println("Rank: " + rank);
        int sessionDuration = 30; // in minutes
        int capacity = 6;

        if (rank < capacity) {
            // Increment appointment time by session duration
            appointmentTime = appointmentTime.plusMinutes(sessionDuration);

            appointmentRequestDTO.setAppointmenTime(appointmentTime);
            appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
            appointmentRequestDTO.setRank(rank + 1);

            return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
        } else {
            System.out.println("Capacity reached!!!");
            // TODO: Update schedule.availability table
            // Possibly return null or throw an exception depending on your design
            return null;
        }
    }
}
