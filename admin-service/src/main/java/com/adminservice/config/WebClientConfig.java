package com.adminservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("UserServiceClient")
    public WebClient userServiceWebClient(@Value("${user.server.url}") String userServiceurl) {
        return WebClient.builder()
                .baseUrl(userServiceurl)
                .build();
    }
}
