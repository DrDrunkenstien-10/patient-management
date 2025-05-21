package com.adminservice.receptionist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminservice.receptionist.model.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, UUID> {
    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByContactEmail(String contactEmail);

    boolean existsByContactPhone(String contactPhone);
    boolean existsByContactEmailAndReceptionistIdNot(String contactEmail, UUID receptionistId);

    boolean existsByContactPhoneAndReceptionistIdNot(String contactPhone, UUID receptionistId);
}
