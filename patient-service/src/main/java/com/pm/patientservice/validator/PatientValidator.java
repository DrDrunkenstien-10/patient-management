package com.pm.patientservice.validator;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.exception.AadhaarNumberAlreadyException;
import com.pm.patientservice.exception.ContactPhoneAlreadyExistsException;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.InvalidFilterCategoryException;
import com.pm.patientservice.repository.PatientRepository;

@Component
public class PatientValidator {
    private final PatientRepository patientRepository;

    private static final Set<String> ALLOWED_FILTER_CATEGORIES = Set.of(
            "name",
            "gender",
            "date_of_birth",
            "aadhaar_number",
            "contact_phone",
            "email",
            "address",
            "medical_history",
            "allergies",
            "medications",
            "consents",
            "emergency_contact",
            "created_at",
            "updated_at");

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

    public void validateFilterCategory(String category) {
        if (!ALLOWED_FILTER_CATEGORIES.contains(category.toLowerCase())) {
            throw new InvalidFilterCategoryException("Unsupported filter category: " + category);
        }
    }
}
