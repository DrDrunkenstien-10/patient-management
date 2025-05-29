package com.scheduleservice.client.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DoctorServiceClient {

    private final WebClient webClient;

    public DoctorServiceClient(@Qualifier("doctorServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }


    public boolean isDoctorExists(UUID doctorId) {
        return webClient.get()
                .uri("/doctors/{id}/exists", doctorId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
    
}
