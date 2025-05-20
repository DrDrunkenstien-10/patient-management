package com.adminservice.systemadmin.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.adminservice.systemadmin.enums.AdminAccessLevel;
import com.adminservice.systemadmin.enums.SystemAdminRole;

@Entity
@Table(name = "system_admin", schema = "admin")

public class SystemAdmin {

    @Id
    @GeneratedValue
    @Column(name = "system_admin_id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID systemAdminId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemAdminRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AdminAccessLevel accessLevel;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

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

    // Getters and Setters
    
}

