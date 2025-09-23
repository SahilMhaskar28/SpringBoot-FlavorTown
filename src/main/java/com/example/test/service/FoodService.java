package com.example.test.service;

import java.util.List;

import com.example.test.model.Food;

public interface FoodService {
	
	boolean addFood(Food f);
	boolean deleteFood(int foodId);
	Food searchFood(int foodId);	
	List<Food> getAllFood();	
	List<Food> searchFoodByName(String foodName);
	List<Food> searchFoodByCategory(String Category);
}
