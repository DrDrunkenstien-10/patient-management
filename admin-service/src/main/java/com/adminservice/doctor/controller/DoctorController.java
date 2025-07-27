package com.adminservice.doctor.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.doctor.dto.DoctorRequestDTO;
import com.adminservice.doctor.dto.DoctorResponseDTO;
import com.adminservice.doctor.dto.PaginatedResponseDTO;
import com.adminservice.doctor.service.DoctorService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.groups.Default;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = doctorService.createDoctor(doctorRequestDTO);
        return ResponseEntity.ok().body(doctorResponseDTO);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<DoctorResponseDTO>> getDoctors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

        PaginatedResponseDTO<DoctorResponseDTO> response = doctorService.getDoctors(page, size, sortBy);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        List<DoctorResponseDTO> doctorResponseDTOs = doctorService.getDoctorAll();
        return ResponseEntity.ok().body(doctorResponseDTOs);
    }

    @GetMapping("/filter")
    public ResponseEntity<PaginatedResponseDTO<DoctorResponseDTO>> filterDoctor(
            @RequestParam(name = "category", defaultValue = "name", required = true) String category,
            @RequestParam(name = "value", defaultValue = "", required = false) String value,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy) {

        PaginatedResponseDTO<DoctorResponseDTO> response = doctorService.filterDoctors(category, value, direction, page,
                size, sortBy);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable("id") UUID doctorId) {
        DoctorResponseDTO doctorResponseDTO = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok().body(doctorResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable("id") UUID doctorId,
            @Validated({ Default.class }) @RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = doctorService.updateDoctor(doctorId, doctorRequestDTO);
        return ResponseEntity.ok().body(doctorResponseDTO);
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> isDoctorExists(@PathVariable("id") UUID doctorId) {
        boolean exists = doctorService.isDoctorExists(doctorId);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") UUID doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Doctor deleted successfully");
    }
}
