package com.adminservice.doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.service.DoctorService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = doctorService.createDoctor(doctorRequestDTO);
        return ResponseEntity.ok().body(doctorResponseDTO);
    }

}
