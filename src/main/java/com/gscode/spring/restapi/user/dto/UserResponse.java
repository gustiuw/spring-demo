package com.gscode.spring.restapi.user.dto;

import java.time.Instant;

public record UserResponse(
        Long id,
        String name,
        String email,
        Instant createdAt,
        Instant updatedAt,
        boolean isDeleted,
        Instant deletedAt,
        String username,
        String password
) {}
