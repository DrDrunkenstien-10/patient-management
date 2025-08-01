package com.pm.patientservice.client.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.pm.patientservice.client.dto.UserRequestDTO;
import com.pm.patientservice.exception.UserServiceClientException;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(@Qualifier("UserServiceClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public void createUser(UserRequestDTO userRequestDTO) {
        try {
            webClient.post()
                    .uri("/api/v1/users/create")
                    .bodyValue(userRequestDTO)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            HttpStatusCode status = ex.getStatusCode();
            if (status.is4xxClientError()) {
                throw new UserServiceClientException("Client error: " + status, ex);
            } else if (status.is5xxServerError()) {
                throw new UserServiceClientException("Server error: " + status, ex);
            } else {
                throw new UserServiceClientException("Unknown HTTP error: " + status, ex);
            }
        } catch (Exception ex) {
            throw new UserServiceClientException("Unexpected error while calling Appointment service", ex);
        }
    }

}
