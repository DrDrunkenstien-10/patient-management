package com.scheduleservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("doctorServiceClient")
    public WebClient doctorServiceWebClient(@Value("${admin.server.url}") String doctorServiceUrl) {
        return WebClient.builder()
                .baseUrl(doctorServiceUrl)
                .build();
    }
}
