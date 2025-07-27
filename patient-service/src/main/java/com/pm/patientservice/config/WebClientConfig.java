package com.pm.patientservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("AppointmentServiceClient")
    public WebClient appointmentServiceWebClient(@Value("${appointment.server.url}") String appointmentServiceUrl) {
        return WebClient.builder()
                .baseUrl(appointmentServiceUrl)
                .build();
    }
}
