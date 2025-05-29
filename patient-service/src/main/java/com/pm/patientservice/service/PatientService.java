// Note: Uncomment the commented code to use Kafka broker and Grpc.
package com.pm.patientservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.PatientNotFoundException;
// import com.pm.patientservice.grpc.BillingServiceGrpcClient;
// import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.validator.PatientValidator;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PatientValidator patientValidator;
    // private BillingServiceGrpcClient billingServiceGrpcClient;
    // private KafkaProducer kafkaProducer;

    // public PatientService(PatientRepository patientRepository,
    // BillingServiceGrpcClient billingServiceGrpcClient,
    // KafkaProducer kafkaProducer) {
    // this.patientRepository = patientRepository;
    // this.billingServiceGrpcClient = billingServiceGrpcClient;
    // this.kafkaProducer = kafkaProducer;
    // }

    public PatientService(PatientRepository patientRepository,
            PatientValidator patientValidator) {
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        patientValidator.validateForCreation(patientRequestDTO);

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        // billingServiceGrpcClient.createBillingAccount(newPatient.getPatientId().toString(),
        // newPatient.getName(), newPatient.getEmail());

        // kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return patientResponseDTOs;
    }

    public PatientResponseDTO getPatientById(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        patientValidator.validateForUpdate(patientId, patientRequestDTO);

        patient.setName(patientRequestDTO.getName());
        patient.setGender(patientRequestDTO.getGender());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setAadhaarNumber(patientRequestDTO.getAadhaarNumber());
        patient.setContactPhone(patientRequestDTO.getContactPhone());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setMedicalHistory(patientRequestDTO.getMedicalHistory());
        patient.setAllergies(patientRequestDTO.getAllergies());
        patient.setMedications(patientRequestDTO.getMedications());
        patient.setConsents(patientRequestDTO.getConsents());
        patient.setEmergencyContact(patientRequestDTO.getEmergencyContact());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    public boolean isPatientExists(UUID patientId) {
        return patientRepository.existsById(patientId);
    }
}
