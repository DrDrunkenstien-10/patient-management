package com.scheduleservice.slot.validator;

import org.springframework.stereotype.Component;

import com.scheduleservice.client.service.DoctorServiceClient;
import com.scheduleservice.slot.dto.SlotRequestDTO;
import com.scheduleservice.slot.exception.DoctorNotFoundException;

@Component
public class SlotValidator {

    private final DoctorServiceClient doctorServiceClient;

    public SlotValidator(DoctorServiceClient doctorServiceClient) {
        this.doctorServiceClient = doctorServiceClient;
    }

    public void validateForCreation(SlotRequestDTO slotRequestDTO){
        if(!doctorServiceClient.isDoctorExists(slotRequestDTO.getDoctorId())){
            throw new DoctorNotFoundException("Doctor not found");
        }
    }
    
}
