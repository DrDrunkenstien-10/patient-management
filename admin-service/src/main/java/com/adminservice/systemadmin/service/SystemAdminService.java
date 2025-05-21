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

@Service
public class SystemAdminService {
    private final SystemAdminRepository systemAdminRepository;

    public SystemAdminService(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
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
