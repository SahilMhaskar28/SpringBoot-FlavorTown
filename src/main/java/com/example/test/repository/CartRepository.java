package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	Cart findByCEmailAndFoodid(String cEmail, int foodId);
	
    List<Cart> findByCEmail(String cEmail);  
    
    void deleteCartByCEmail(String cEmail);
}
