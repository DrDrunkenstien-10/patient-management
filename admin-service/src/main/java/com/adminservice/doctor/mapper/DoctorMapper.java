package com.adminservice.doctor.mapper;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.model.Doctor;

public class DoctorMapper {

    public static DoctorResponseDTO toDto(Doctor doctor) {
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();

        doctorResponseDTO.setId(doctor.getDoctorId().toString());
        doctorResponseDTO.setName(doctor.getName());

        return doctorResponseDTO;
    }

    public static Doctor toModel(DoctorRequestDTO doctorRequestDTO) {

        Doctor doctor = new Doctor();

        doctor.setName(doctorRequestDTO.getName());
        doctor.setGender(doctorRequestDTO.getGender());
        doctor.setDateOfBirth(doctorRequestDTO.getDateOfBirth());
        doctor.setQualification(doctorRequestDTO.getQualification());
        doctor.setSpecialization(doctorRequestDTO.getSpecialization());
        doctor.setAffiliatedHospital(doctorRequestDTO.getAffiliatedHospital());
        doctor.setLicenseNumber(doctorRequestDTO.getLicenseNumber());
        doctor.setPracticeLocation(doctorRequestDTO.getPracticeLocation());
        doctor.setContactEmail(doctorRequestDTO.getContactEmail());
        doctor.setContactPhone(doctorRequestDTO.getContactPhone());
        doctor.setRoleCode(doctorRequestDTO.getRoleCode());

        return doctor;
    }
}