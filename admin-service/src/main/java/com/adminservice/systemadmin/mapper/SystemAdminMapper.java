package com.adminservice.systemadmin.mapper;


import com.adminservice.systemadmin.dto.SystemAdminRequestDTO;
import com.adminservice.systemadmin.dto.SystemAdminResponseDTO;
import com.adminservice.systemadmin.model.SystemAdmin;

public class SystemAdminMapper {
    public static SystemAdminResponseDTO toDto(SystemAdmin systemAdmin) {
        SystemAdminResponseDTO systemAdminResponseDTO = new SystemAdminResponseDTO();
        systemAdminResponseDTO.setSystemAdminId(systemAdmin.getSystemAdminId().toString());
        systemAdminResponseDTO.setName(systemAdmin.getName());
        systemAdminResponseDTO.setRole(systemAdmin.getRole());
        systemAdminResponseDTO.setAccessLevel(systemAdmin.getAccessLevel());
        systemAdminResponseDTO.setLastLogin(systemAdmin.getLastLogin());
        systemAdminResponseDTO.setCreatedAt(systemAdmin.getCreatedAt());
        systemAdminResponseDTO.setUpdatedAt(systemAdmin.getUpdatedAt());
        return systemAdminResponseDTO;
    
}
    public static SystemAdmin toModel(SystemAdminRequestDTO systemAdminRequestDTO) {
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setSystemAdminId(systemAdminRequestDTO.getSystemAdminId());
        systemAdmin.setName(systemAdminRequestDTO.getName());
        systemAdmin.setRole(systemAdminRequestDTO.getRole());
        systemAdmin.setAccessLevel(systemAdminRequestDTO.getAccessLevel());
        systemAdmin.setLastLogin(systemAdminRequestDTO.getLastLogin());
        systemAdmin.setCreatedAt(systemAdminRequestDTO.getCreatedAt());
        systemAdmin.setUpdatedAt(systemAdminRequestDTO.getUpdatedAt());
        return systemAdmin;
    }
}
