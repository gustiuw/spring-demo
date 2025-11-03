package com.gscode.spring.restapi.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;


public record UserPatchRequest(
    String username,
    @Email(message = "email must be valid") String email,
    String password,
    String name,
    @JsonProperty("is_deleted") Boolean isDeleted
) {}
