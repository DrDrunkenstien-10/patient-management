package com.adminservice.systemadmin.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.adminservice.systemadmin.enums.AdminAccessLevel;
import com.adminservice.systemadmin.enums.SystemAdminRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SystemAdminRequestDTO {
    private UUID systemAdminId; // Optional in DTO unless needed for updates

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Role must not be null")
    private SystemAdminRole role;

    @NotNull(message = "Access level must not be null")
    private AdminAccessLevel accessLevel;

    private OffsetDateTime lastLogin;

    public UUID getSystemAdminId() {
        return systemAdminId;
    }

    public void setSystemAdminId(UUID systemAdminId) {
        this.systemAdminId = systemAdminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SystemAdminRole getRole() {
        return role;
    }

    public void setRole(SystemAdminRole role) {
        this.role = role;
    }

    public AdminAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AdminAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

}
