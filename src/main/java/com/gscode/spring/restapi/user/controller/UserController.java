package com.gscode.spring.restapi.user.controller;

import com.gscode.spring.restapi.user.dto.UserRequest;
import com.gscode.spring.restapi.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.gscode.spring.restapi.user.service.UserService;
import com.gscode.spring.restapi.user.dto.UserPatchRequest;

@Tag(name = "Users", description = "User CRUD endpoints")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    public UserController(UserService service) { this.service = service; }

    @Operation(summary = "List users")
    @GetMapping
    public Page<UserResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Create user")
    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest req) {
        return service.create(req);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest req) {
        return service.update(id, req);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Restore user")
    @PutMapping("/{id}/restore")
    public void restore(@PathVariable Long id) {
        service.restore(id);
    }

    @Operation(summary = "Patch user")
    @PatchMapping("/{id}")
    public UserResponse patch(@PathVariable Long id, @Valid @RequestBody UserPatchRequest req) {
        return service.patch(id, req);
    }
}
