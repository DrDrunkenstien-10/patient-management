package com.adminservice.doctor.service;

import org.springframework.stereotype.Service;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.mapper.DoctorMapper;
import com.adminservice.doctor.model.Doctor;
import com.adminservice.doctor.repository.DoctorRepository;
import com.adminservice.doctor.validater.DoctorValidater;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private DoctorValidater doctorValidater;

    public DoctorService(DoctorRepository doctorRepository, DoctorValidater doctorValidator) {
        this.doctorRepository = doctorRepository;
        this.doctorValidater = doctorValidator;
    }

    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO) {

        doctorValidater.validateForCreation(doctorRequestDTO);

        Doctor newDoctor = doctorRepository.save(DoctorMapper.toModel(doctorRequestDTO));

        return DoctorMapper.toDto(newDoctor);
    }

}