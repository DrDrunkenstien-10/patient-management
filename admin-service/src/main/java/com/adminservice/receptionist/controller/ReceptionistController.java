package com.adminservice.receptionist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.receptionist.dto.ReceptionistRequestDTO;
import com.adminservice.receptionist.dto.ReceptionistResponseDTO;
import com.adminservice.receptionist.service.ReceptionistService;

import jakarta.validation.groups.Default;

import org.springframework.web.bind.annotation.PutMapping;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/receptionists")
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    public ReceptionistController(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }

    @PostMapping
    public ResponseEntity<ReceptionistResponseDTO> createReceptionists(
            @RequestBody ReceptionistRequestDTO receptionistRequestDTO) {
        ReceptionistResponseDTO receptionistResponseDTO = receptionistService
                .createReceptionists(receptionistRequestDTO);
        return ResponseEntity.ok().body(receptionistResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReceptionistResponseDTO>> getReceptionists() {
        List<ReceptionistResponseDTO> receptionistResponseDTOs = receptionistService.
                getReceptionists();
        return ResponseEntity.ok().body(receptionistResponseDTOs);

    }

    @PutMapping("{id}")
    public ResponseEntity<ReceptionistResponseDTO> updateReceptionist(@PathVariable("id") UUID receptionistId,
            @Validated({ Default.class }) @RequestBody ReceptionistRequestDTO receptionistRequestDTO) {
        ReceptionistResponseDTO updatedReceptionist = receptionistService.updateReceptionist(receptionistId,
                receptionistRequestDTO);
        return ResponseEntity.ok().body(updatedReceptionist);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReceptionist(@PathVariable("id") UUID receptionistId) {
        receptionistService.deleteReceptionist(receptionistId);
        return ResponseEntity.ok("Receptionist deleted successfully");
    }
}
