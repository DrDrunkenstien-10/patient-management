package com.appointmentservice.appointment.client.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.appointmentservice.appointment.client.dto.SlotDTO;

@Component
public class SlotServiceClient {

    private final WebClient webClient;

    public SlotServiceClient(@Qualifier("slotServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public SlotDTO getSlotById(UUID slotId) {
        return webClient.get()
                .uri("/slots/{slotId}", slotId)
                .retrieve()
                .bodyToMono(SlotDTO.class)
                .block();
    }

    public boolean isSlotExists(UUID slotId) {
        return webClient.get()
                .uri("/slots/{id}/exists", slotId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
