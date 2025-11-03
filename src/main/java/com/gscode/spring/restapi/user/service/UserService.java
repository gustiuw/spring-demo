package com.gscode.spring.restapi.user.service;

import com.gscode.spring.restapi.user.dto.UserPatchRequest;
import com.gscode.spring.restapi.user.dto.UserRequest;
import com.gscode.spring.restapi.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gscode.spring.restapi.user.exception.BadRequestException;
import com.gscode.spring.restapi.user.exception.ResourceNotFoundException;
import com.gscode.spring.restapi.user.repository.UserRepository;
import com.gscode.spring.restapi.user.mapper.UserMapper;
import com.gscode.spring.restapi.user.User;
import java.time.Instant;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(Pageable pageable) {
        return repo.findByIsDeletedFalse(pageable).map(UserMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return UserMapper.toResponse(u);
    }

    @Transactional
    public UserResponse create(UserRequest req) {
        if (repo.existsByEmail(req.email()))
            throw new BadRequestException("email already exists");
        User u = new User();
        UserMapper.apply(u, req);
        return UserMapper.toResponse(repo.save(u));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest req) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        if (!u.getEmail().equalsIgnoreCase(req.email()) && repo.existsByEmail(req.email()))
            throw new BadRequestException("email already exists");
        UserMapper.apply(u, req);
        return UserMapper.toResponse(repo.save(u));
    }

    @Transactional
    public void delete(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        u.setDeleted(true);
        u.setDeletedAt(Instant.now());
        repo.save(u);
    }

    @Transactional
    public void restore(Long id) {
        User u = repo.findByIdAndIsDeletedTrue(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        u.setDeleted(false);
        u.setDeletedAt(null);
        repo.save(u);
    }

    @Transactional
    public UserResponse patch(Long id, UserPatchRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (req.name() != null && !req.name().isBlank()) {
            u.setName(req.name());
        }

        if (req.email() != null && !req.email().isBlank()) {
            if (!u.getEmail().equalsIgnoreCase(req.email()) && repo.existsByEmail(req.email()))
                throw new BadRequestException("email already exists");
            u.setEmail(req.email());
        }

        if (req.password() != null && !req.password().isBlank()) {
            u.setPassword(req.password());
        }

        if (req.username() != null && !req.username().isBlank()) {
            u.setUsername(req.username());
        }

        if (req.isDeleted() != null) {
            u.setDeleted(req.isDeleted());
            u.setDeletedAt(req.isDeleted() ? Instant.now() : null);
        }

        return UserMapper.toResponse(repo.save(u));
    }
}
