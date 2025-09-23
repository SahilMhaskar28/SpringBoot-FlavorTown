package com.example.test.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.Food;
import com.example.test.repository.FoodRepository;
import com.example.test.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private FoodRepository foodRepo;
	Food food;
	List<Food> list;
	
	@Override
	public boolean addFood(Food f) {
		Food saved=foodRepo.save(f);
		if(saved != null) {
			return true;
		}
		else {
			return false;
		}
		
	}


	@Override
	public boolean deleteFood(int foodId) {
		food=searchFood(foodId);
		if(food != null) {
			foodRepo.delete(food);
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public Food searchFood(int foodId) {
		Optional<Food> id = foodRepo.findById(foodId);
		if(!id.isEmpty()){
			food=id.get();
			return food;
		}
		else {
			return null;
		}
	}

	@Override
	public List<Food> getAllFood() {
		List<Food> li = foodRepo.findAll();
		if(li != null && !li.isEmpty()) {
			return li;
		}
		else {
			return li;
		}
	}

	@Override
	public List<Food> searchFoodByName(String foodName) {	
		
		return foodRepo.findByFoodNameContainingIgnoreCase(foodName);
		
	}

	@Override
	public List<Food> searchFoodByCategory(String Category) {
		
		return null;
	}

}
