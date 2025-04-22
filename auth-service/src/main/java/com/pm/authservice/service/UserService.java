package com.pm.authservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pm.authservice.model.User;
import com.pm.authservice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
