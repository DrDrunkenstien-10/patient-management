package com.adminservice.doctor.model;

import jakarta.persistence.*;

import java.util.UUID;

import com.adminservice.doctor.enums.Gender;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "doctor", schema = "staff")
public class Doctor {

    @Id
    @Column(name = "doctor_id", columnDefinition = "UUID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID doctorId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String qualification;

    @Column(nullable = false)
    private String specialization;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "affiliated_hospital", nullable = false)
    private String affiliatedHospital;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "practice_location", nullable = false)
    private String practiceLocation;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    // Getters and setters
    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAffiliatedHospital() {
        return affiliatedHospital;
    }

    public void setAffiliatedHospital(String affiliatedHospital) {
        this.affiliatedHospital = affiliatedHospital;
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

    public String getPracticeLocation() {
        return practiceLocation;
    }

    public void setPracticeLocation(String practiceLocation) {
        this.practiceLocation = practiceLocation;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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
