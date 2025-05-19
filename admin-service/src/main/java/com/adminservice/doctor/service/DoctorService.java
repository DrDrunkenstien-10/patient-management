package com.adminservice.doctor.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.exception.DoctorNotFoundException;
import com.adminservice.doctor.mapper.DoctorMapper;
import com.adminservice.doctor.model.Doctor;
import com.adminservice.doctor.repository.DoctorRepository;
import com.adminservice.doctor.validator.DoctorValidator;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private DoctorValidator doctorValidator;

    public DoctorService(DoctorRepository doctorRepository, DoctorValidator doctorValidator) {
        this.doctorRepository = doctorRepository;
        this.doctorValidator = doctorValidator;
    }

    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO) {
        doctorValidator.validateForCreation(doctorRequestDTO);

        Doctor newDoctor = doctorRepository.save(DoctorMapper.toModel(doctorRequestDTO));

        return DoctorMapper.toDto(newDoctor);
    }

    public List<DoctorResponseDTO> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        List<DoctorResponseDTO> doctorResponseDTOs = doctors.stream().map(doctor -> DoctorMapper.toDto(doctor))
                .toList();

        return doctorResponseDTOs;
    }

    public DoctorResponseDTO updateDoctor(UUID doctorId, DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with ID: " + doctorRequestDTO.getDoctorId()));

        doctorValidator.validateForUpdate(doctorRequestDTO, doctorId);

        doctor.setName(doctorRequestDTO.getName());
        doctor.setGender(doctorRequestDTO.getGender());
        doctor.setDateOfBirth(doctorRequestDTO.getDateOfBirth());
        doctor.setQualification(doctorRequestDTO.getQualification());
        doctor.setLicenseNumber(doctorRequestDTO.getLicenseNumber());
        doctor.setAffiliatedHospital(doctorRequestDTO.getAffiliatedHospital());
        doctor.setContactEmail(doctorRequestDTO.getContactEmail());
        doctor.setContactPhone(doctorRequestDTO.getContactPhone());
        doctor.setPracticeLocation(doctorRequestDTO.getPracticeLocation());
        doctor.setRoleCode(doctorRequestDTO.getRoleCode());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return DoctorMapper.toDto(updatedDoctor);
    }
}