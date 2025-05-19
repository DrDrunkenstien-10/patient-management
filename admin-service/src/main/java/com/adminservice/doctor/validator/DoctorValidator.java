package com.adminservice.doctor.validator;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.exception.ContactPhoneAlreadyExistsException;
import com.adminservice.doctor.exception.EmailAlreadyExistsException;
import com.adminservice.doctor.exception.LicenseNumberAlreadyExistsException;
import com.adminservice.doctor.repository.DoctorRepository;

@Component
public class DoctorValidator {

    private final DoctorRepository doctorRepository;

    public DoctorValidator(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void validateForCreation(DoctorRequestDTO doctorRequestDTO) {

        if (doctorRepository.existsByContactEmail(doctorRequestDTO.getContactEmail())) {
            throw new EmailAlreadyExistsException(
                    "A Doctor with this email already exists: " + doctorRequestDTO.getContactEmail());
        }

        if (doctorRepository.existsByLicenseNumber(doctorRequestDTO.getLicenseNumber())) {
            throw new LicenseNumberAlreadyExistsException(
                    "A Doctor with this license number already exists: " + doctorRequestDTO.getLicenseNumber());
        }

        if (doctorRepository.existsByContactPhone(doctorRequestDTO.getContactPhone())) {
            throw new ContactPhoneAlreadyExistsException(
                    "A Doctor with this contact phone already exists: " + doctorRequestDTO.getContactPhone());
        }

        // You can add more validations here
        // e.g. , check if name is valid , age is within range, etc.
    }

    public void validateForUpdate(DoctorRequestDTO doctorRequestDTO, UUID doctorId) {
        if (doctorRepository.existsByContactEmailAndDoctorIdNot(doctorRequestDTO.getContactEmail(), doctorId)) {
            throw new EmailAlreadyExistsException(
                    "A Doctor with this email already exists: " + doctorRequestDTO.getContactEmail());
        }

        if (doctorRepository.existsByLicenseNumberAndDoctorIdNot(doctorRequestDTO.getLicenseNumber(), doctorId)) {
            throw new LicenseNumberAlreadyExistsException(
                    "A Doctor with this license number already exists: " + doctorRequestDTO.getLicenseNumber());
        }

        if (doctorRepository.existsByContactPhoneAndDoctorIdNot(doctorRequestDTO.getContactPhone(), doctorId)) {
            throw new ContactPhoneAlreadyExistsException(
                    "A Doctor with this contact phone already exists: " + doctorRequestDTO.getContactPhone());
        }
    }
}