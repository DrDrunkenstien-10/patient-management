package com.appointmentservice.appointment.client.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.appointmentservice.appointment.client.dto.AvailabilityDTO;

@Component
public class AvailabilityServiceClient {
    private final WebClient webClient;

    public AvailabilityServiceClient(@Qualifier("availabilityServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public UUID getAvailabilityId(UUID docId, UUID slotId, LocalDate date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/availabilities/availability-id")
                        .queryParam("doctorId", docId)
                        .queryParam("slotId", slotId)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(UUID.class)
                .block();
    }

    public String updateAvailabilityStatus(AvailabilityDTO availabilityDTO) {
        return webClient.put()
                .uri("/availabilities/{id}", availabilityDTO.getAvailabilityId())
                .bodyValue(availabilityDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
