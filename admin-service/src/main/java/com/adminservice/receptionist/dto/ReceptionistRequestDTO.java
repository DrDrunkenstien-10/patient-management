package com.adminservice.receptionist.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.adminservice.receptionist.enums.Gender;
import com.adminservice.receptionist.enums.ReceptionistAccessLevel;
import com.adminservice.receptionist.enums.ReceptionistStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReceptionistRequestDTO {
    private UUID receptionistId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Employee code is required")
    private String employeeCode;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Invalid email format")
    private String contactEmail;

    @NotBlank(message = "Contact phone is required")
    private String contactPhone;

    @NotBlank(message = "Assigned facility is required")
    private String assignedFacility;

    @NotBlank(message = "Role title is required")
    private String roleTitle;

    @NotNull(message = "Access level is required")
    private ReceptionistAccessLevel accessLevel;

    private OffsetDateTime lastLogin;

    @NotNull(message = "Status is required")
    private ReceptionistStatus status;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
