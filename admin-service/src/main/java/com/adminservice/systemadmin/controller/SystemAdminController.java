package com.adminservice.systemadmin.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.systemadmin.dto.SystemAdminRequestDTO;
import com.adminservice.systemadmin.dto.SystemAdminResponseDTO;
import com.adminservice.systemadmin.service.SystemAdminService;

import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/system-admins")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;

    public SystemAdminController(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemAdminResponseDTO> updateSystemAdmin(@PathVariable("id") UUID systemAdminId,
            @Validated({ Default.class }) @RequestBody SystemAdminRequestDTO systemAdminRequestDTO) {
        SystemAdminResponseDTO systemAdminResponseDTO = systemAdminService
                .updateSystemAdmin(systemAdminId, systemAdminRequestDTO);
        return ResponseEntity.ok().body(systemAdminResponseDTO);

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSystemAdminById(@PathVariable("id") UUID systemAdminId) {
        systemAdminService.deleteSystemAdminById(systemAdminId);
        return ResponseEntity.ok("System admin deleted successfully");
    }
}
