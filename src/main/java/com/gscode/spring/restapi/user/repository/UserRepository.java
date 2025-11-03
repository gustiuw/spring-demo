package com.gscode.spring.restapi.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;  
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gscode.spring.restapi.user.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByIdAndIsDeletedFalse(Long id);
    Page<User> findByIsDeletedFalse(Pageable pageable);
    Optional<User> findByIdAndIsDeletedTrue(Long id);
}
