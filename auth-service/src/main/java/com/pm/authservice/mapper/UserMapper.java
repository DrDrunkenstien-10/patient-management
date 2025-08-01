package com.pm.authservice.mapper;

import com.pm.authservice.dto.UserResponseDTO;
import com.pm.authservice.model.User;

public class UserMapper {

    public static UserResponseDTO toDto(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        return userResponseDTO;
    }

    public static User toModel(UserResponseDTO userResponseDTO) {
        User user = new User();
        user.setId(userResponseDTO.getId());
        user.setEmail(userResponseDTO.getEmail());
        user.setRole(userResponseDTO.getRole());
        return user;
    }
}
