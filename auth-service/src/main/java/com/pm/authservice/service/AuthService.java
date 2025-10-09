package com.pm.authservice.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.model.RefreshToken;
import com.pm.authservice.repository.RefreshTokenRepository;
import com.pm.authservice.util.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
            RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        return userService.findByEmail(dto.getEmail())
                .filter(u -> passwordEncoder.matches(dto.getPassword(), u.getPassword()))
                .map(user -> {
                    String accessToken = jwtUtil.generateAccessToken(user.getId().toString(), user.getEmail(),
                            user.getRole());
                    String refreshToken = jwtUtil.generateRefreshToken(user.getId().toString(), user.getEmail());

                    // Save to DB
                    RefreshToken tokenEntity = new RefreshToken();
                    tokenEntity.setId(UUID.randomUUID());
                    tokenEntity.setToken(refreshToken);
                    tokenEntity.setUser(user);
                    tokenEntity.setExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)); // 7 days
                    refreshTokenRepository.save(tokenEntity);

                    return new LoginResponseDTO(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Optional<String> refreshToken(String refreshToken) {
        try {
            // Step 1: Validate JWT format and signature
            jwtUtil.validateToken(refreshToken);

            // Step 2: Extract email from token
            String email = jwtUtil.extractEmail(refreshToken);

            String id = jwtUtil.extractId(refreshToken);

            // Step 3: Lookup refresh token in DB
            Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(refreshToken);

            if (storedToken.isEmpty()) {
                return Optional.empty(); // Not found in DB
            }

            RefreshToken token = storedToken.get();

            // Step 4: Check expiry
            if (token.getExpiry().before(new Date())) {
                return Optional.empty(); // Expired
            }

            // Step 5: Generate new access token
            return Optional.of(jwtUtil.generateAccessToken(id, email, token.getUser().getRole()));

        } catch (JwtException e) {
            return Optional.empty(); // Invalid or malformed token
        }
    }

    public boolean logout(String refreshToken) {
        Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(refreshToken);

        if (storedToken.isPresent()) {
            refreshTokenRepository.delete(storedToken.get());
            return true;
        } else {
            return false; // Token not found or already revoked
        }
    }
}
