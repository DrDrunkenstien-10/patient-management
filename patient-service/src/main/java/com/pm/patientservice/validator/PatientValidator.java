package com.pm.patientservice.validator;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.exception.AadhaarNumberAlreadyException;
import com.pm.patientservice.exception.ContactPhoneAlreadyExistsException;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.repository.PatientRepository;

@Component
public class PatientValidator {
    private final PatientRepository patientRepository;

    public PatientValidator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void validateForCreation(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByAadhaarNumber(patientRequestDTO.getAadhaarNumber())) {
            throw new AadhaarNumberAlreadyException(
                    "A patient with this aadhaar number already exists"
                            + patientRequestDTO.getAadhaarNumber());
        }

        if (patientRepository.existsByContactPhone(patientRequestDTO.getContactPhone())) {
            throw new ContactPhoneAlreadyExistsException(
                    "A patient with this phone number already exists"
                            + patientRequestDTO.getAadhaarNumber());
        }

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists"
                            + patientRequestDTO.getEmail());
        }
    }

    public void validateForUpdate(UUID patientId, PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByAadhaarNumberAndPatientIdNot(patientRequestDTO.getAadhaarNumber(), patientId)) {
            throw new AadhaarNumberAlreadyException(
                    "A patient with this aadhaar number already exists " + patientRequestDTO.getEmail());
        }

        if (patientRepository.existsByContactPhoneAndPatientIdNot(patientRequestDTO.getContactPhone(), patientId)) {
            throw new ContactPhoneAlreadyExistsException(
                    "A patient with this contact number already exists " + patientRequestDTO.getEmail());
        }

        if (patientRepository.existsByEmailAndPatientIdNot(patientRequestDTO.getEmail(), patientId)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists " + patientRequestDTO.getEmail());
        }
    }
}
