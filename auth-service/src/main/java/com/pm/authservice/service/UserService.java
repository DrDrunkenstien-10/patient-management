package com.pm.authservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pm.authservice.dto.UserRequestDTO;
import com.pm.authservice.dto.UserResponseDTO;
import com.pm.authservice.mapper.UserMapper;
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

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(PasswordHasher.hashPassword(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }
}
