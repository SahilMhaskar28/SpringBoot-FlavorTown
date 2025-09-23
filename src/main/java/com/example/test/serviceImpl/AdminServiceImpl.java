package com.example.test.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test.model.Admin;
import com.example.test.repository.AdminRepo; // You'll need to create this
import com.example.test.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public Admin searchByEmailId(String email) {
        return adminRepo.findByUserName(email).orElse(null);
    }
}