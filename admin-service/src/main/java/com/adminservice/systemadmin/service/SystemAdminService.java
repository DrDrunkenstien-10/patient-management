package com.adminservice.systemadmin.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adminservice.systemadmin.dto.SystemAdminRequestDTO;
import com.adminservice.systemadmin.dto.SystemAdminResponseDTO;
import com.adminservice.systemadmin.exception.SystemAdminNotFoundException;
import com.adminservice.systemadmin.mapper.SystemAdminMapper;
import com.adminservice.systemadmin.model.SystemAdmin;
import com.adminservice.systemadmin.repository.SystemAdminRepository;

import jakarta.transaction.Transactional;


import java.util.List;

import com.adminservice.systemadmin.validator.SystemAdminValidator;

@Service
public class SystemAdminService {
      private SystemAdminRepository systemAdminRepository;
    private SystemAdminValidator systemAdminValidator;

    public SystemAdminService(SystemAdminRepository systemAdminRepository, SystemAdminValidator systemAdminValidator) {
        this.systemAdminRepository = systemAdminRepository;
        this.systemAdminValidator = systemAdminValidator;
    }

    public SystemAdminResponseDTO createSystemAdmin(SystemAdminRequestDTO systemAdminRequestDTO) {
        systemAdminValidator.validateForCreation(systemAdminRequestDTO);

        SystemAdmin newSystemAdmin = systemAdminRepository.save(SystemAdminMapper.toModel(systemAdminRequestDTO));

        return SystemAdminMapper.toDto(newSystemAdmin);
    }

     public List<SystemAdminResponseDTO> getSystemAdmins() {
        List<SystemAdmin> systemAdmins = systemAdminRepository.findAll();

        List<SystemAdminResponseDTO> systemAdminResponseDTOs = systemAdmins.stream().map(systemAdmin -> SystemAdminMapper.toDto(systemAdmin))
                .toList();

        return systemAdminResponseDTOs;
    }

     public SystemAdminResponseDTO updateSystemAdmin(UUID systemAdminId, SystemAdminRequestDTO systemAdminRequestDTO) {
        SystemAdmin systemAdmin = systemAdminRepository.findById(systemAdminId)
                .orElseThrow(() -> new SystemAdminNotFoundException(
                        "System admin not found with ID: " + systemAdminRequestDTO.getSystemAdminId()));

        systemAdmin.setName(systemAdminRequestDTO.getName());
        systemAdmin.setRole(systemAdminRequestDTO.getRole());
        systemAdmin.setAccessLevel(systemAdminRequestDTO.getAccessLevel());

        SystemAdmin updatedSystemAdmin = systemAdminRepository.save(systemAdmin);

        return SystemAdminMapper.toDto(updatedSystemAdmin);
    }
    
    @Transactional
    public void deleteSystemAdminById(UUID systemAdminId) {
        SystemAdmin systemAdmin = systemAdminRepository.findById(systemAdminId)
                .orElseThrow(() -> new SystemAdminNotFoundException(
                        "System Admin not found with ID: " + systemAdminId));
        systemAdminRepository.delete(systemAdmin);
    }


}
