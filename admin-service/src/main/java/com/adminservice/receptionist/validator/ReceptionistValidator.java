package com.adminservice.receptionist.validator;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.adminservice.receptionist.dto.ReceptionistRequestDTO;
import com.adminservice.receptionist.exception.ContactPhoneAlreadyExistsException;
import com.adminservice.receptionist.exception.EmailAlreadyExistsException;
import com.adminservice.receptionist.exception.EmployeeCodeAlreadyExistsException;
import com.adminservice.receptionist.repository.ReceptionistRepository;

@Component
public class ReceptionistValidator {
    private final ReceptionistRepository receptionistRepository;

    public ReceptionistValidator(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    public void validateForCreation(ReceptionistRequestDTO receptionistRequestDTO) {
        if (receptionistRepository.existsByEmployeeCode(receptionistRequestDTO.getEmployeeCode())) {
            throw new EmployeeCodeAlreadyExistsException(
                    "A receptionist with this employee code already exists: "
                            + receptionistRequestDTO.getEmployeeCode());
        }

        if (receptionistRepository.existsByContactEmail(receptionistRequestDTO.getContactEmail())) {
            throw new EmailAlreadyExistsException(
                    "A receptionist with this email already exists: "
                            + receptionistRequestDTO.getContactEmail());
        }

        if (receptionistRepository.existsByContactPhone(receptionistRequestDTO.getContactPhone())) {
            throw new ContactPhoneAlreadyExistsException(
                    "A receptionist with this contact phone already exists:"
                            + receptionistRequestDTO.getContactPhone());
        }
    }

    public void validateForUpdate(ReceptionistRequestDTO receptionistRequestDTO, UUID receptionistId) {
        if (receptionistRepository.existsByContactEmailAndReceptionistIdNot(receptionistRequestDTO.getContactEmail(),
                receptionistId)) {
            throw new EmailAlreadyExistsException(
                    "Receptionist with this email already exist :" + receptionistRequestDTO.getContactEmail());
        }

        if (receptionistRepository.existsByContactPhoneAndReceptionistIdNot(receptionistRequestDTO.getContactPhone(),
                receptionistId)) {
            throw new ContactPhoneAlreadyExistsException(
                    "Receptionist with this contact phone already exist" + receptionistRequestDTO.getContactPhone());
        }
    }

}
