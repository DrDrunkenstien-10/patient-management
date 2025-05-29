package com.appointmentservice.appointment.client.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.appointmentservice.appointment.client.dto.PatientDTO;

@Component
public class PatientServiceClient {
    private final WebClient webClient;

    public PatientServiceClient(@Qualifier("patientServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public PatientDTO getPatientById(UUID patientId) {
        return webClient.get()
                .uri("/patients/{patientId}", patientId)
                .retrieve()
                .bodyToMono(PatientDTO.class)
                .block();
    }

    public boolean isPatientExists(UUID patientId) {
        return webClient.get()
                .uri("/patients/{id}/exists", patientId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}