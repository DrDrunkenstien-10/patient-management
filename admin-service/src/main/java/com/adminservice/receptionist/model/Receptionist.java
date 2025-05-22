package com.adminservice.receptionist.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.adminservice.receptionist.enums.Gender;
import com.adminservice.receptionist.enums.ReceptionistAccessLevel;
import com.adminservice.receptionist.enums.ReceptionistStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "receptionist", schema = "staff")
public class Receptionist {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "receptionist_id", updatable = false, nullable = false)
    private UUID receptionistId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "assigned_facility", nullable = false)
    private String assignedFacility;

    @Column(name = "role_title", nullable = false)
    private String roleTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private ReceptionistAccessLevel accessLevel;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReceptionistStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // Getters and setters
    public UUID getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(UUID receptionistId) {
        this.receptionistId = receptionistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAssignedFacility() {
        return assignedFacility;
    }

    public void setAssignedFacility(String assignedFacility) {
        this.assignedFacility = assignedFacility;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public ReceptionistAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(ReceptionistAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public ReceptionistStatus getStatus() {
        return status;
    }

    public void setStatus(ReceptionistStatus status) {
        this.status = status;
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
