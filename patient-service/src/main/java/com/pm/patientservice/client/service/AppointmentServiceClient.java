package com.pm.patientservice.client.service;

import java.util.List;
import java.util.UUID;

import com.pm.patientservice.client.dto.AppointmentDTO;
import com.pm.patientservice.exception.AppointmentServiceClientException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AppointmentServiceClient {

    private final WebClient webClient;

    public AppointmentServiceClient(@Qualifier("AppointmentServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(UUID doctorId) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/appointments/doctor/{id}")
                            .build(doctorId))
                    .retrieve()
                    .bodyToFlux(AppointmentDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status.is4xxClientError()) {
                throw new AppointmentServiceClientException("Client error: " + status, ex);
            } else if (status.is5xxServerError()) {
                throw new AppointmentServiceClientException("Server error: " + status, ex);
            } else {
                throw new AppointmentServiceClientException("Unknown HTTP error: " + status, ex);
            }
        } catch (Exception ex) {
            throw new AppointmentServiceClientException("Unexpected error while calling Appointment service", ex);
        }
    }

}
