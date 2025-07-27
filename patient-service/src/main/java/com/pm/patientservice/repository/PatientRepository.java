package com.pm.patientservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.pm.patientservice.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>,JpaSpecificationExecutor<Patient> {
    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByContactPhone(String contactPhone);

    boolean existsByEmail(String email);

    boolean existsByAadhaarNumberAndPatientIdNot(String aadhaarNumber, UUID patientId);

    boolean existsByContactPhoneAndPatientIdNot(String contactPhone, UUID patientId);

    boolean existsByEmailAndPatientIdNot(String email, UUID patientId);
}
