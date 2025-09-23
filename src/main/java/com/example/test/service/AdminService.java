package com.example.test.service;

import com.example.test.model.Admin;

public interface AdminService {
    Admin searchByEmailId(String email);
}