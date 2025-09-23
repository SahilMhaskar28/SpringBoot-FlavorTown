package com.example.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    
    Optional<Admin> findByUserName(String userName);
}