package com.appointmentservice.appointment.validator;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.time.Duration;

import org.springframework.stereotype.Component;

import com.appointmentservice.appointment.client.dto.SlotDTO;
import com.appointmentservice.appointment.client.service.DoctorServiceClient;
import com.appointmentservice.appointment.client.service.PatientServiceClient;
import com.appointmentservice.appointment.client.service.SlotServiceClient;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.exception.AppointmentExistsException;
import com.appointmentservice.appointment.exception.DoctorNotFoundException;
import com.appointmentservice.appointment.exception.InvalidAppointmentTimeException;
import com.appointmentservice.appointment.exception.InvalidFilterCategoryException;
import com.appointmentservice.appointment.exception.PatientNotFoundException;
import com.appointmentservice.appointment.exception.SlotNotFoundException;
import com.appointmentservice.appointment.repository.AppointmentRepository;

@Component
public class AppointmentValidator {

    private static final Set<String> ALLOWED_FILTER_CATEGORIES = Set.of(
            "appointment_id", "doctor_id", "patient_id", "slot_id", "appointment_time",
            "status", "rank", "appointment_date");

    private final DoctorServiceClient doctorServiceClient;
    private final PatientServiceClient patientServiceClient;
    private final SlotServiceClient slotServiceClient;
    private final AppointmentRepository appointmentRepository;

    public AppointmentValidator(DoctorServiceClient doctorServiceClient,
            PatientServiceClient patientServiceClient,
            SlotServiceClient slotServiceClient,
            AppointmentRepository appointmentRepository) {
        this.doctorServiceClient = doctorServiceClient;
        this.patientServiceClient = patientServiceClient;
        this.slotServiceClient = slotServiceClient;
        this.appointmentRepository = appointmentRepository;
    }

    public void validateForCreation(AppointmentRequestDTO appointmentRequestDTO) {
        if (!doctorServiceClient.isDoctorExists(appointmentRequestDTO.getDoctorId())) {
            throw new DoctorNotFoundException("Doctor not found");
        }

        if (!patientServiceClient.isPatientExists(appointmentRequestDTO.getPatientId())) {
            throw new PatientNotFoundException("Patient not found");
        }

        if (!slotServiceClient.isSlotExists(appointmentRequestDTO.getSlotId())) {
            throw new SlotNotFoundException("Slot not found");
        }

        SlotDTO slot = slotServiceClient.getSlotById(appointmentRequestDTO.getSlotId());
        LocalTime time = appointmentRequestDTO.getAppointmentTime().withSecond(0).withNano(0);

        LocalTime slotStart = parseTime(slot.getStartTime());
        LocalTime slotEnd = parseTime(slot.getEndTime());

        if (time.isBefore(slotStart) || time.isAfter(slotEnd)) {
            throw new InvalidAppointmentTimeException("Appointment time outside slot range");
        }

        if (Duration.between(slotStart, time).toMinutes() % slot.getSessionDuration() != 0) {
            throw new InvalidAppointmentTimeException("Appointment time not aligned with slot duration");
        }

        if (appointmentRepository.existsByDoctorIdAndPatientIdAndSlotIdAndAppointmentDate(
                appointmentRequestDTO.getDoctorId(),
                appointmentRequestDTO.getPatientId(),
                appointmentRequestDTO.getSlotId(),
                appointmentRequestDTO.getAppointmentDate())) {
            throw new AppointmentExistsException("appointment exists for the doctor at the slot and date");
        }

        if (appointmentRepository.existsByDoctorIdAndSlotIdAndAppointmentDateAndAppointmentTime(
                appointmentRequestDTO.getDoctorId(), appointmentRequestDTO.getSlotId(),
                appointmentRequestDTO.getAppointmentDate(), time)) {
            throw new AppointmentExistsException("This time slot is already booked.");
        }

    }

    public void validateFilterCategory(String category) {
        if (!ALLOWED_FILTER_CATEGORIES.contains(category.toLowerCase())) {
            throw new InvalidFilterCategoryException("Unsupported filter category: " + category);
        }
    }

    private LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

}
