package com.appointmentservice.appointment.validator;

import org.springframework.stereotype.Component;

import com.appointmentservice.appointment.client.service.DoctorServiceClient;
import com.appointmentservice.appointment.client.service.PatientServiceClient;
import com.appointmentservice.appointment.client.service.SlotServiceClient;
import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.exception.DoctorNotFoundException;
import com.appointmentservice.appointment.exception.PatientNotFoundException;
import com.appointmentservice.appointment.exception.SlotNotFoundException;

@Component
public class AppointmentValidator {
    private final DoctorServiceClient doctorServiceClient;
    private final PatientServiceClient patientServiceClient;
    private final SlotServiceClient slotServiceClient;

    public AppointmentValidator(DoctorServiceClient doctorServiceClient,
            PatientServiceClient patientServiceClient,
            SlotServiceClient slotServiceClient) {
        this.doctorServiceClient = doctorServiceClient;
        this.patientServiceClient = patientServiceClient;
        this.slotServiceClient = slotServiceClient;
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
    }
}
