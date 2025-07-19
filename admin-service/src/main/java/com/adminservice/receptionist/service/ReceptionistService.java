package com.adminservice.receptionist.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.adminservice.receptionist.dto.PaginatedResponseDTO;
import com.adminservice.receptionist.dto.ReceptionistRequestDTO;
import com.adminservice.receptionist.dto.ReceptionistResponseDTO;
import com.adminservice.receptionist.exception.ReceptionistNotFoundException;
import com.adminservice.receptionist.mapper.ReceptionistMapper;
import com.adminservice.receptionist.model.Receptionist;
import com.adminservice.receptionist.repository.ReceptionistRepository;
import com.adminservice.receptionist.specification.ReceptionistSpecification;
import com.adminservice.receptionist.validator.ReceptionistValidator;

import jakarta.transaction.Transactional;

@Service
public class ReceptionistService {
    private final ReceptionistRepository receptionistRepository;
    private final ReceptionistValidator receptionistValidator;

    public ReceptionistService(ReceptionistRepository receptionistRepository,
            ReceptionistValidator receptionistValidator) {
        this.receptionistRepository = receptionistRepository;
        this.receptionistValidator = receptionistValidator;
    }

    public ReceptionistResponseDTO createReceptionists(ReceptionistRequestDTO receptionistRequestDTO) {
        receptionistValidator.validateForCreation(receptionistRequestDTO);

        Receptionist newReceptionist = receptionistRepository.save(ReceptionistMapper.toModel(receptionistRequestDTO));

        return ReceptionistMapper.toDto(newReceptionist);
    }

    // public List<ReceptionistResponseDTO> getReceptionists() {
    //     List<Receptionist> receptionists = receptionistRepository.findAll();

    //     List<ReceptionistResponseDTO> receptionistResponseDTOs = receptionists.stream()
    //             .map(receptionist -> ReceptionistMapper.toDto(receptionist)).toList();

    //     return receptionistResponseDTOs;
    // }

      public PaginatedResponseDTO<ReceptionistResponseDTO> getReceptionists(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Receptionist> receptionists = receptionistRepository.findAll(pageable);

        List<ReceptionistResponseDTO> receptionistResponseDTOs = receptionists.stream().map(receptionist -> ReceptionistMapper.toDto(receptionist))
                .toList();

        return new PaginatedResponseDTO<>(
                receptionistResponseDTOs,
                receptionists.getNumber(),
                receptionists.getSize(),
                receptionists.getTotalElements(),
                receptionists.getTotalPages(),
                receptionists.isLast(),
                receptionists.isFirst());
    }

     public PaginatedResponseDTO<ReceptionistResponseDTO> filterReceptionists(
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

        Specification<Receptionist> spec = ReceptionistSpecification.getReceptionistSpecification(category, value);

        Page<Receptionist> receptionists = receptionistRepository.findAll(spec, pageable);

        List<ReceptionistResponseDTO> receptionistResponseDTOs = receptionists.stream().map(receptionist -> ReceptionistMapper.toDto(receptionist))
                .toList();

        return new PaginatedResponseDTO<>(
                receptionistResponseDTOs,
                receptionists.getNumber(),
                receptionists.getSize(),
                receptionists.getTotalElements(),
                receptionists.getTotalPages(),
                receptionists.isLast(),
                receptionists.isFirst());
    }

    public ReceptionistResponseDTO getReceptionistById(UUID receptionistId) {
        Receptionist receptionist = receptionistRepository.findById(receptionistId)
                .orElseThrow(() -> new ReceptionistNotFoundException(
                        "Receptionist not found with ID: " + receptionistId));

        return ReceptionistMapper.toDto(receptionist);
    }

    public ReceptionistResponseDTO updateReceptionist(UUID receptionistId,
            ReceptionistRequestDTO receptionistRequestDTO) {
        Receptionist receptionist = receptionistRepository.findById(receptionistId)
                .orElseThrow(() -> new ReceptionistNotFoundException(
                        "Receptionist not found with ID: " + receptionistRequestDTO.getReceptionistId()));

        receptionistValidator.validateForUpdate(receptionistRequestDTO, receptionistId);

        receptionist.setName(receptionistRequestDTO.getName());
        receptionist.setGender(receptionistRequestDTO.getGender());
        receptionist.setDateOfBirth(receptionistRequestDTO.getDateOfBirth());
        receptionist.setEmployeeCode(receptionistRequestDTO.getEmployeeCode());
        receptionist.setDepartment(receptionistRequestDTO.getDepartment());
        receptionist.setContactEmail(receptionistRequestDTO.getContactEmail());
        receptionist.setContactPhone(receptionistRequestDTO.getContactPhone());
        receptionist.setAssignedFacility(receptionistRequestDTO.getAssignedFacility());
        receptionist.setRoleTitle(receptionistRequestDTO.getRoleTitle());
        receptionist.setAccessLevel(receptionistRequestDTO.getAccessLevel());
        receptionist.setLastLogin(receptionistRequestDTO.getLastLogin());
        receptionist.setStatus(receptionistRequestDTO.getStatus());

        Receptionist updatedReceptionist = receptionistRepository.save(receptionist);

        return ReceptionistMapper.toDto(updatedReceptionist);
    }

    @Transactional
    public void deleteReceptionist(UUID receptionistId) {
        Receptionist receptionist = receptionistRepository.findById(receptionistId)
                .orElseThrow(() -> new ReceptionistNotFoundException(
                        "Receptionist not found with ID: " + receptionistId));

        receptionistRepository.delete(receptionist);
    }
}
