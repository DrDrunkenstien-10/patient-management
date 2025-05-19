package com.adminservice.doctor.validater;

import org.springframework.stereotype.Component;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.exception.EmailAlreadyExistsException;
import com.adminservice.doctor.exception.LicenseNumberAlreadyExistsException;
import com.adminservice.doctor.repository.DoctorRepository;

@Component
public class DoctorValidater {

    private final DoctorRepository doctorRepository;

    public DoctorValidater(DoctorRepository doctorRepository) {
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

        // You can add more validations here
        // e.g. , check if name is valid , age is within range, etc.
    }
}