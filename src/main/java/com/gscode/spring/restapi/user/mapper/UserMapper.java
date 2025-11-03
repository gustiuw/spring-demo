package com.gscode.spring.restapi.user.mapper;

import com.gscode.spring.restapi.user.User;
import com.gscode.spring.restapi.user.dto.UserRequest;
import com.gscode.spring.restapi.user.dto.UserResponse;

public class UserMapper {
    public static UserResponse toResponse(User u) {
        return new UserResponse(
            u.getId(), 
            u.getName(), 
            u.getEmail(), 
            u.getCreatedAt(), 
            u.getUpdatedAt(), 
            u.isDeleted(), 
            u.getDeletedAt(),
            u.getUsername(),
            u.getPassword()
        );
    }
    public static void apply(User u, UserRequest req) {
        u.setName(req.name());
        u.setEmail(req.email());
        u.setUsername(req.username());
        u.setPassword(req.password());
    }
}
