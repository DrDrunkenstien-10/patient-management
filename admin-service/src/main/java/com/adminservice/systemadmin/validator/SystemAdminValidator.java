package com.adminservice.systemadmin.validator;

import org.springframework.stereotype.Component;


import com.adminservice.systemadmin.dto.SystemAdminRequestDTO;
import com.adminservice.systemadmin.repository.SystemAdminRepository;


@Component
public class SystemAdminValidator {
    private final SystemAdminRepository systemAdminRepository;

    public SystemAdminValidator(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    public void validateForCreation(SystemAdminRequestDTO systemAdminRequestDTO) {

       
    }
}
