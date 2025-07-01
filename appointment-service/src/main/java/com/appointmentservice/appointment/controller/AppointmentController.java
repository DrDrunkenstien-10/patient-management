package com.appointmentservice.appointment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointmentservice.appointment.dto.AppointmentRequestDTO;
import com.appointmentservice.appointment.dto.AppointmentResponseDTO;
import com.appointmentservice.appointment.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.createAppointment(appointmentRequestDTO);
        return ResponseEntity.ok().body(appointmentResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointment() {
        List<AppointmentResponseDTO> appointmentResponseDTOs = appointmentService.getAppointments();
        return ResponseEntity.ok(appointmentResponseDTOs);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") UUID appointmentId) {

        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
