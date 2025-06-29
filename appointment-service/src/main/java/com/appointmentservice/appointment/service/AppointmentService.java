package com.appointmentservice.appointment.service;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.appointmentservice.appointment.client.dto.DoctorDTO;
import com.appointmentservice.appointment.client.dto.PatientDTO;
import com.appointmentservice.appointment.client.dto.SlotDTO;
import com.appointmentservice.appointment.client.service.DoctorServiceClient;
import com.appointmentservice.appointment.client.service.PatientServiceClient;
import com.appointmentservice.appointment.client.service.SlotServiceClient;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.mapper.AppointmentMapper;
import com.appointmentservice.appointment.model.Appointment;
import com.appointmentservice.appointment.repository.AppointmentRepository;
import com.appointmentservice.appointment.validator.AppointmentValidator;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final SlotServiceClient slotServiceClient;
    private final PatientServiceClient patientServiceClient;
    private final DoctorServiceClient doctorServiceClient;
    private final AppointmentValidator appointmentValidator;

    public AppointmentService(AppointmentRepository appointmentRepository, SlotServiceClient slotServiceClient,
            PatientServiceClient patientServiceClient, DoctorServiceClient doctorServiceClient,
            AppointmentValidator appointmentValidator) {
        this.appointmentRepository = appointmentRepository;
        this.slotServiceClient = slotServiceClient;
        this.patientServiceClient = patientServiceClient;
        this.doctorServiceClient = doctorServiceClient;
        this.appointmentValidator = appointmentValidator;
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        appointmentValidator.validateForCreation(appointmentRequestDTO);

        Optional<Appointment> existingAppointmentOpt = appointmentRepository
                .findTop1ByDoctorIdAndSlotIdOrderByRankDesc(
                        appointmentRequestDTO.getDoctorId(),
                        appointmentRequestDTO.getSlotId());

        Appointment appointment;

        SlotDTO slotDTO = slotServiceClient.getSlotById(appointmentRequestDTO.getSlotId());
        PatientDTO patientDTO = patientServiceClient.getPatientById(appointmentRequestDTO.getPatientId());
        DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointmentRequestDTO.getDoctorId());

        if (existingAppointmentOpt.isPresent()) {
            // Create subsequent appointment
            appointment = createSubsequentAppointment(existingAppointmentOpt.get(), appointmentRequestDTO, slotDTO);
        } else {
            // Create new appointment
            appointment = createFirstAppointment(appointmentRequestDTO, slotDTO);
        }

        return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
    }

    private Appointment createFirstAppointment(AppointmentRequestDTO appointmentRequestDTO, SlotDTO slotDTO) {
        //LocalTime startTime = LocalTime.parse("11:00:00");
        LocalTime startTime = LocalTime.parse(slotDTO.getStartTime());

        appointmentRequestDTO.setAppointmenTime(startTime);
        appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
        appointmentRequestDTO.setRank(1);

        return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
    }

    private Appointment createSubsequentAppointment(Appointment existingAppointment,
            AppointmentRequestDTO appointmentRequestDTO, SlotDTO slotDTO) {

        LocalTime appointmentTime = existingAppointment.getAppointmentTime();
        int rank = existingAppointment.getRank();

        int sessionDuration = slotDTO.getSessionDuration();
        int capacity = slotDTO.getCapacity();

        if (rank < capacity) {

            // Increment appointment time by session duration
            appointmentTime = appointmentTime.plusMinutes(sessionDuration);

            appointmentRequestDTO.setAppointmenTime(appointmentTime);
            appointmentRequestDTO.setAppointmentStatus(AppointmentStatus.NOT_VISITED);
            appointmentRequestDTO.setRank(rank + 1);

            return appointmentRepository.save(AppointmentMapper.toModel(appointmentRequestDTO));
        } else {
            System.out.println("Capacity reached!!!");

            // Possibly return null or throw an exception depending on your design
            return null;
        }
    }
}
