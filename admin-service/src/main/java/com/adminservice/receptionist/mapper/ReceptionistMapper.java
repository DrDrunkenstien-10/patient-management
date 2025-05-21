package com.adminservice.receptionist.mapper;

import com.adminservice.receptionist.dto.ReceptionistRequestDTO;
import com.adminservice.receptionist.dto.ReceptionistResponseDTO;
import com.adminservice.receptionist.model.Receptionist;

public class ReceptionistMapper {
    public static ReceptionistResponseDTO toDto(Receptionist receptionist) {
        ReceptionistResponseDTO receptionistResponseDTO = new ReceptionistResponseDTO();

        receptionistResponseDTO.setReceptionistId(receptionist.getReceptionistId().toString());
        receptionistResponseDTO.setName(receptionist.getName());
        receptionistResponseDTO.setGender(receptionist.getGender());
        receptionistResponseDTO.setDateOfBirth(receptionist.getDateOfBirth());
        receptionistResponseDTO.setEmployeeCode(receptionist.getEmployeeCode());
        receptionistResponseDTO.setDepartment(receptionist.getDepartment());
        receptionistResponseDTO.setContactEmail(receptionist.getContactEmail());
        receptionistResponseDTO.setContactPhone(receptionist.getContactPhone());
        receptionistResponseDTO.setAssignedFacility(receptionist.getAssignedFacility());
        receptionistResponseDTO.setRoleTitle(receptionist.getRoleTitle());
        receptionistResponseDTO.setAccessLevel(receptionist.getAccessLevel());
        receptionistResponseDTO.setLastLogin(receptionist.getLastLogin());
        receptionistResponseDTO.setStatus(receptionist.getStatus());
        receptionistResponseDTO.setCreatedAt(receptionist.getCreatedAt());
        receptionistResponseDTO.setUpdatedAt(receptionist.getUpdatedAt());

        return receptionistResponseDTO;
    }

    public static Receptionist toModel(ReceptionistRequestDTO receptionistRequestDTO) {
        Receptionist receptionist = new Receptionist();

        receptionist.setName(receptionistRequestDTO.getName());
        receptionist.setGender(receptionistRequestDTO.getGender());
        receptionist.setDateOfBirth(receptionistRequestDTO.getDateOfBirth());
        receptionist.setEmployeeCode(receptionistRequestDTO.getEmployeeCode());
        receptionist.setDepartment(receptionistRequestDTO.getDepartment());
        receptionist.setContactEmail(receptionistRequestDTO.getContactEmail());
        receptionist.setContactPhone(receptionistRequestDTO.getContactPhone());
        receptionist.setAssignedFacility(receptionistRequestDTO.getAssignedFacility());
        receptionist.setRoleTitle(receptionistRequestDTO.getRoleTitle());
        receptionist.setAccessLevel(receptionistRequestDTO.getAccessLevel());
        receptionist.setLastLogin(receptionistRequestDTO.getLastLogin());
        receptionist.setStatus(receptionistRequestDTO.getStatus());

        return receptionist;
    }
}
