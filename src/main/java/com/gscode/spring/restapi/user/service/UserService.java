package com.gscode.spring.restapi.user.service;

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

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(UserMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return UserMapper.toResponse(u);
    }

    @Transactional
    public UserResponse create(UserRequest req) {
        if (repo.existsByEmail(req.email())) throw new BadRequestException("email already exists");
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
        if (!repo.existsById(id)) throw new ResourceNotFoundException("user not found");
        repo.deleteById(id);
    }
}
