package com.example.test.service;

import java.util.List;

import com.example.test.model.Cart;
import com.example.test.model.Customer;

public interface CartService {
	Cart addToCart(int foodId, int i, String email);
	List<Cart> showCartList();
	List<Cart>searchCartByEmailId (String cEmail);
	Cart searchCartById(int cartid);
	boolean deleteCartById(int cartid);
	boolean deleteCartByEmail(String email);
	boolean updateCart(int cartid,int fquantity);
}
