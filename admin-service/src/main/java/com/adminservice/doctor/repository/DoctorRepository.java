package com.adminservice.doctor.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.doctor.model.Doctor;


public interface DoctorRepository extends JpaRepository<Doctor,UUID> {
    boolean existsByContactEmail(String contactEmail);
    boolean existsByLicenseNumber(String licenseNumber);
}
