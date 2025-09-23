package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.Food;

public interface FoodRepository extends JpaRepository<Food, Integer>{

	 List<Food> findByFoodNameContainingIgnoreCase(String foodName);
}
