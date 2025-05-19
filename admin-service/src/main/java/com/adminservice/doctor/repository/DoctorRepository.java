package com.adminservice.doctor.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adminservice.doctor.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    boolean existsByContactEmail(String contactEmail);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByContactPhone(String contactPhone);

    boolean existsByContactEmailAndDoctorIdNot(String contactEmail, UUID doctorId);

    boolean existsByLicenseNumberAndDoctorIdNot(String licenseNumber, UUID doctorId);

    boolean existsByContactPhoneAndDoctorIdNot(String contactPhone, UUID doctorId);
}
