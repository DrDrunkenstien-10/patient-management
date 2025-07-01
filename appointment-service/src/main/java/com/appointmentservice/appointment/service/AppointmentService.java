package com.appointmentservice.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.appointmentservice.appointment.client.dto.AvailabilityDTO;
import com.appointmentservice.appointment.client.dto.DoctorDTO;
import com.appointmentservice.appointment.client.dto.PatientDTO;
import com.appointmentservice.appointment.client.dto.SlotDTO;
import com.appointmentservice.appointment.client.service.AvailabilityServiceClient;
import com.appointmentservice.appointment.client.service.DoctorServiceClient;
import com.appointmentservice.appointment.client.service.PatientServiceClient;
import com.appointmentservice.appointment.client.service.SlotServiceClient;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.enums.AppointmentStatus;
import com.appointmentservice.appointment.exception.SlotCapacityExceededException;
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
    private final AvailabilityServiceClient availabilityServiceClient;

    public AppointmentService(AppointmentRepository appointmentRepository, SlotServiceClient slotServiceClient,
            PatientServiceClient patientServiceClient, DoctorServiceClient doctorServiceClient,
            AppointmentValidator appointmentValidator,
            AvailabilityServiceClient availabilityServiceClient) {
        this.appointmentRepository = appointmentRepository;
        this.slotServiceClient = slotServiceClient;
        this.patientServiceClient = patientServiceClient;
        this.doctorServiceClient = doctorServiceClient;
        this.appointmentValidator = appointmentValidator;
        this.availabilityServiceClient = availabilityServiceClient;
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        appointmentValidator.validateForCreation(appointmentRequestDTO);

        List<String> statuses = Arrays.asList("RESCHEDULED", "CANCELLED");
        Optional<Appointment> existingResheduledOrCancelledAppointment = appointmentRepository
                .findTop1ByDoctorIdAndSlotIdAndStatusInOrderByRankAsc(
                        appointmentRequestDTO.getDoctorId(),
                        appointmentRequestDTO.getSlotId(),
                        statuses);

        Optional<Appointment> existingAppointmentOpt = appointmentRepository
                .findTop1ByDoctorIdAndSlotIdOrderByRankDesc(
                        appointmentRequestDTO.getDoctorId(),
                        appointmentRequestDTO.getSlotId());

        SlotDTO slotDTO = slotServiceClient.getSlotById(appointmentRequestDTO.getSlotId());
        PatientDTO patientDTO = patientServiceClient.getPatientById(appointmentRequestDTO.getPatientId());
        DoctorDTO doctorDTO = doctorServiceClient.getDoctorById(appointmentRequestDTO.getDoctorId());

        if (existingResheduledOrCancelledAppointment.isPresent()) {
            Appointment appointment = updateAppointment(appointmentRequestDTO,
                    existingResheduledOrCancelledAppointment.get());
            return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO); // ✅ Early return
        }

        Appointment appointment;
        if (existingAppointmentOpt.isPresent()) {
            appointment = createSubsequentAppointment(existingAppointmentOpt.get(), appointmentRequestDTO, slotDTO);
        } else {
            appointment = createFirstAppointment(appointmentRequestDTO, slotDTO);
        }

        return AppointmentMapper.toDto(appointment, slotDTO, doctorDTO, patientDTO);
    }

    private Appointment createFirstAppointment(AppointmentRequestDTO appointmentRequestDTO, SlotDTO slotDTO) {
        // LocalTime startTime = LocalTime.parse("11:00:00");
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
        }

        else {
            UUID doctorId = existingAppointment.getDoctorId();
            UUID slotId = existingAppointment.getSlotId();
            LocalDate date = existingAppointment.getAppointmentDate();
            boolean availability = false;
            String unAvailabilityReason = "Slot full";
            UUID availabilityId = availabilityServiceClient.getAvailabilityId(doctorId, slotId, date);

            AvailabilityDTO availabilityDTO = new AvailabilityDTO();

            availabilityDTO.setAvailabilityId(availabilityId);
            availabilityDTO.setDocId(doctorId);
            availabilityDTO.setSlotId(slotId);
            availabilityDTO.setDate(date);
            availabilityDTO.setAvailability(availability);
            availabilityDTO.setUnavailabilityReason(unAvailabilityReason);

            availabilityServiceClient.updateAvailabilityStatus(availabilityDTO);

            throw new SlotCapacityExceededException(
                    "No available appointments: slot capacity of " + capacity + " reached for slotId "
                            + appointmentRequestDTO.getSlotId());
        }
    }

    public Appointment updateAppointment(AppointmentRequestDTO appointmentRequestDTO,
            Appointment existingResheduledOrCancelledAppointment) {

        existingResheduledOrCancelledAppointment.setPatientId(appointmentRequestDTO.getPatientId());
        existingResheduledOrCancelledAppointment.setStatus(AppointmentStatus.NOT_VISITED);

        return appointmentRepository.save(existingResheduledOrCancelledAppointment);
    }
}
