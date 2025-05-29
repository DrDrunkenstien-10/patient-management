package com.appointmentservice.appointment.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("slotServiceClient")
    public WebClient slotServiceWebClient(@Value("${slot.service.url}") String slotServiceUrl) {
        return WebClient.builder()
                .baseUrl(slotServiceUrl)
                .build();
    }

    @Bean
    @Qualifier("patientServiceClient")
    public WebClient patientServiceWebClient(@Value("${patient.service.url}") String patientServiceUrl) {
        return WebClient.builder()
                .baseUrl(patientServiceUrl)
                .build();
    }

    @Bean
    @Qualifier("doctorServiceClient")
    public WebClient doctorServiceWebClient(@Value("${admim.server.url}") String doctorServiceUrl) {
        return WebClient.builder()
                .baseUrl(doctorServiceUrl)
                .build();
    }
}
