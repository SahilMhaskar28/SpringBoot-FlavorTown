package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.OrderFood;

public interface OrderRepository extends JpaRepository<OrderFood, Integer> {
	 List<OrderFood> findByEmailId(String emailId);

}
