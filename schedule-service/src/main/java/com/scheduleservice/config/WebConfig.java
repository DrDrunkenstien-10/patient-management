// package com.scheduleservice.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig {
//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @SuppressWarnings("null")
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedOrigins("http://localhost:4200")
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//                         .allowedHeaders("*")
//                         .allowCredentials(true); // Optional: If you're using cookies/auth
//             }
//         };
//     }

//         // ✅ Add this for default WebClient bean
//     @Bean
//     public WebClient webClient() {
//         return WebClient.builder().build();
//     }
// }
package com.scheduleservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    // ✅ Qualified WebClient for doctorServiceClient
    @Bean
    @Qualifier("doctorWebClient")
    public WebClient doctorWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:4006")
                .build();
    }
}
