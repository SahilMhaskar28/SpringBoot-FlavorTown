package com.example.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	Optional<Customer> findByEmailId(String emailId);

	void deleteByEmailId(String emailId);
}
