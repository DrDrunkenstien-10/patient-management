package com.appointmentservice.appointment.client.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.appointmentservice.appointment.client.dto.DoctorDTO;

@Component
public class DoctorServiceClient {

    private final WebClient webClient;

    public DoctorServiceClient(@Qualifier("doctorServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public DoctorDTO getDoctorById(UUID doctorId) {
        return webClient.get()
                .uri("/doctors/{doctorId}", doctorId)
                .retrieve()
                .bodyToMono(DoctorDTO.class)
                .block();
    }
}
