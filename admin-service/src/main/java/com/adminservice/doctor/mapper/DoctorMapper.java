package com.adminservice.doctor.mapper;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.model.Doctor;

public class DoctorMapper {

    public static DoctorResponseDTO toDto(Doctor doctor) {
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();

        doctorResponseDTO.setDoctorId(doctor.getDoctorId().toString());
        doctorResponseDTO.setName(doctor.getName());
        doctorResponseDTO.setGender(doctor.getGender());
        doctorResponseDTO.setDateOfBirth(doctor.getDateOfBirth());
        doctorResponseDTO.setQualification(doctor.getQualification());
        doctorResponseDTO.setSpecialization(doctor.getSpecialization());
        doctorResponseDTO.setAffiliatedHospital(doctor.getAffiliatedHospital());
        doctorResponseDTO.setLicenseNumber(doctor.getLicenseNumber());
        doctorResponseDTO.setPracticeLocation(doctor.getPracticeLocation());
        doctorResponseDTO.setContactEmail(doctor.getContactEmail());
        doctorResponseDTO.setContactPhone(doctor.getContactPhone());
        doctorResponseDTO.setRoleCode(doctor.getRoleCode());
        doctorResponseDTO.setCreatedAt(doctor.getCreatedAt());
        doctorResponseDTO.setUpdatedAt(doctor.getUpdatedAt());

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