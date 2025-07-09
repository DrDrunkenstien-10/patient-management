package com.adminservice.doctor.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.dto.PaginatedResponseDTO;
import com.adminservice.doctor.exception.DoctorNotFoundException;
import com.adminservice.doctor.mapper.DoctorMapper;
import com.adminservice.doctor.model.Doctor;
import com.adminservice.doctor.repository.DoctorRepository;
import com.adminservice.doctor.specification.DoctorSpecification;
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

    public PaginatedResponseDTO<DoctorResponseDTO> getDoctors(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Doctor> doctors = doctorRepository.findAll(pageable);

        List<DoctorResponseDTO> doctorResponseDTOs = doctors.stream().map(doctor -> DoctorMapper.toDto(doctor))
                .toList();

        return new PaginatedResponseDTO<>(
                doctorResponseDTOs,
                doctors.getNumber(),
                doctors.getSize(),
                doctors.getTotalElements(),
                doctors.getTotalPages(),
                doctors.isLast(),
                doctors.isFirst());
    }

    public PaginatedResponseDTO<DoctorResponseDTO> filterDoctors(
            String category,
            String value,
            String direction,
            int page,
            int size,
            String sortBy) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Doctor> spec = DoctorSpecification.getDoctorSpecification(category, value);

        Page<Doctor> doctors = doctorRepository.findAll(spec, pageable);

        List<DoctorResponseDTO> doctorResponseDTOs = doctors.stream().map(doctor -> DoctorMapper.toDto(doctor))
                .toList();

        return new PaginatedResponseDTO<>(
                doctorResponseDTOs,
                doctors.getNumber(),
                doctors.getSize(),
                doctors.getTotalElements(),
                doctors.getTotalPages(),
                doctors.isLast(),
                doctors.isFirst());
    }

    public DoctorResponseDTO getDoctorById(UUID doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with ID: " + doctorId));

        return DoctorMapper.toDto(doctor);
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

    @Transactional
    public void deleteDoctor(UUID doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " does not exist");
        }
        doctorRepository.deleteById(doctorId);
    }

    public boolean isDoctorExists(UUID doctorId) {
        return doctorRepository.existsById(doctorId);
    }
}