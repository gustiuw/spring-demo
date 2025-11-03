package com.gscode.spring.restapi.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gscode.spring.restapi.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
