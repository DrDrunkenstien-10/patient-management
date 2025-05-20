package com.adminservice.receptionist.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.adminservice.receptionist.enums.Gender;
import com.adminservice.receptionist.enums.ReceptionistAccessLevel;
import com.adminservice.receptionist.enums.ReceptionistStatus;

public class ReceptionistResponseDTO {
    private String receptionistId;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String employeeCode;
    private String department;
    private String contactEmail;
    private String contactPhone;
    private String assignedFacility;
    private String roleTitle;
    private ReceptionistAccessLevel accessLevel;
    private OffsetDateTime lastLogin;
    private ReceptionistStatus status;

    // Getters and setters
    public String getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(String receptionistId) {
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
}
