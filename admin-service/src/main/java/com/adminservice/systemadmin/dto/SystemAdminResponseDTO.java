package com.adminservice.systemadmin.dto;

import java.time.OffsetDateTime;

import com.adminservice.systemadmin.enums.AdminAccessLevel;
import com.adminservice.systemadmin.enums.SystemAdminRole;

public class SystemAdminResponseDTO {
     private String systemAdminId;
    private String name;
    private SystemAdminRole role;
    private AdminAccessLevel accessLevel;
    private OffsetDateTime lastLogin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;



    
    public String getSystemAdminId() {
        return systemAdminId;
    }
    public void setSystemAdminId(String systemAdminId) {
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
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}
