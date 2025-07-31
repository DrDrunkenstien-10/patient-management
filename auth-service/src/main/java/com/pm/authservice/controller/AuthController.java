package com.pm.authservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.dto.RefreshTokenRequestDTO;
import com.pm.authservice.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate tokens using login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponse = authService.authenticate(loginRequestDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Validate token")
    @GetMapping("/validate")
    public ResponseEntity<Void> vaildateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Generate new access token using refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshTokenRequestDTO request) {
        Optional<String> newAccessToken = authService.refreshToken(request.getRefreshToken());

        if (newAccessToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken.get());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout user and revoke refresh token")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDTO request) {
        boolean revoked = authService.logout(request.getRefreshToken());

        if (revoked) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Token not found or already revoked
        }
    }
}
