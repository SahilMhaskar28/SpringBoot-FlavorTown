package com.example.test.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.model.Cart;
import com.example.test.model.Customer;
import com.example.test.model.Food;
import com.example.test.repository.CartRepository;
import com.example.test.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private FoodServiceImpl fdimpl; 

    @Autowired
    private CustomerServiceImpl csimpl;

    @Override
    public Cart addToCart(int foodId, int i, String email) {
        try {
            Food food = fdimpl.searchFood(foodId); 
            if (food == null) {
                return null; 
            }
          
            Customer customer = csimpl.searchByEmailId(email);
            if (customer == null) {
                return null; 
            }

            Cart existingCartItem = cartRepo.findByCEmailAndFoodid(email, foodId);

            if (existingCartItem != null) {
         
                existingCartItem.setFquantity(existingCartItem.getFquantity() + 1);
                existingCartItem.setTotalPrice(existingCartItem.getFprice() * existingCartItem.getFquantity());
                return cartRepo.save(existingCartItem);
            } else {
               
                Cart newCart = new Cart();
                newCart.setFoodid(food.getFoodId());
                newCart.setFname(food.getFoodName());
                newCart.setFprice(food.getFoodPrice());
                newCart.setFquantity(1);
                newCart.setTotalPrice(food.getFoodPrice());
                newCart.setcEmail(customer.getEmailId()); 
                return cartRepo.save(newCart);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

		@Override
		public List<Cart> showCartList() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Cart> searchCartByEmailId(String cEmail) {
		    return cartRepo.findByCEmail(cEmail);  
		}

		@Override
		public Cart searchCartById(int cartid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean deleteCartById(int cartid) {
			try {
			cartRepo.deleteById(cartid);
			return true;
			}
			catch(Exception e) {
				return false;
			}
		}

		@Override
		 @Transactional
		public boolean deleteCartByEmail(String email) {
			cartRepo.deleteCartByCEmail(email);
			return true;
		}

		@Override
		public boolean updateCart(int cartid, int fquantity) {
			// TODO Auto-generated method stub
			return false;
		}

		public Cart updateQuantity(int cartid, int quantity) {
	        Cart cart = cartRepo.findById(cartid).orElse(null);
	        if (cart == null) {
	            throw new RuntimeException("Cart not found");
	        }

	        cart.setFquantity(quantity);
	        cart.setTotalPrice(cart.getFprice() * quantity);

	        return cartRepo.save(cart);
	    }
}
