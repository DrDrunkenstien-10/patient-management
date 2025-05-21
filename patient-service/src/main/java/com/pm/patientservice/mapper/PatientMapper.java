package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setPatientId(patient.getPatientId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setGender(patient.getGender());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth());
        patientResponseDTO.setAadhaarNumber(patient.getAadhaarNumber());
        patientResponseDTO.setContactPhone(patient.getContactPhone());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setMedicalHistory(patient.getMedicalHistory());
        patientResponseDTO.setAllergies(patient.getAllergies());
        patientResponseDTO.setMedications(patient.getMedications());
        patientResponseDTO.setConsents(patient.getConsents());
        patientResponseDTO.setEmergencyContact(patient.getEmergencyContact());
        patientResponseDTO.setCreatedAt(patient.getCreatedAt().toString());
        patientResponseDTO.setUpdatedAt(patient.getUpdatedAt().toString());

        return patientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();

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

        return patient;
    }
}
