package com.pm.authservice.dto;

public class LoginResponseDTO {
    private final String token;
    private final String refreshToken;

    public LoginResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
