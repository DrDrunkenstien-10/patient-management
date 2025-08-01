package com.adminservice.systemadmin.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adminservice.client.dto.UserRequestDTO;
import com.adminservice.client.service.UserServiceClient;
import com.adminservice.systemadmin.dto.SystemAdminRequestDTO;
import com.adminservice.systemadmin.dto.SystemAdminResponseDTO;
import com.adminservice.systemadmin.exception.SystemAdminNotFoundException;
import com.adminservice.systemadmin.mapper.SystemAdminMapper;
import com.adminservice.systemadmin.model.SystemAdmin;
import com.adminservice.systemadmin.repository.SystemAdminRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class SystemAdminService {
    private SystemAdminRepository systemAdminRepository;
    private UserServiceClient userServiceClient;

    public SystemAdminService(SystemAdminRepository systemAdminRepository, UserServiceClient userServiceClient) {
        this.systemAdminRepository = systemAdminRepository;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public SystemAdminResponseDTO createSystemAdmin(SystemAdminRequestDTO systemAdminRequestDTO) {

        try {
            SystemAdmin newSystemAdmin = systemAdminRepository.save(SystemAdminMapper.toModel(systemAdminRequestDTO));
            userServiceClient.createUser(
                    new UserRequestDTO(systemAdminRequestDTO.getContactEmail(), systemAdminRequestDTO.getPassword(),
                            "SYSTEM_ADMIN"));
            return SystemAdminMapper.toDto(newSystemAdmin);
        } catch (Exception e) {
            throw new RuntimeException("Error creating system admin: " + e.getMessage(), e);
        }

    }

    public List<SystemAdminResponseDTO> getSystemAdmins() {
        List<SystemAdmin> systemAdmins = systemAdminRepository.findAll();

        List<SystemAdminResponseDTO> systemAdminResponseDTOs = systemAdmins.stream()
                .map(systemAdmin -> SystemAdminMapper.toDto(systemAdmin))
                .toList();

        return systemAdminResponseDTOs;
    }

    public SystemAdminResponseDTO getSystemAdminById(UUID systemAdminId) {
        SystemAdmin systemAdmin = systemAdminRepository.findById(systemAdminId)
                .orElseThrow(() -> new SystemAdminNotFoundException(
                        "System admin not found with ID: " + systemAdminId));
        return SystemAdminMapper.toDto(systemAdmin);
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
