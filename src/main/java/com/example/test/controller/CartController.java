package com.example.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.model.Cart;
import com.example.test.model.Customer;
import com.example.test.serviceImpl.CartServiceImpl;
import com.example.test.serviceImpl.CustomerServiceImpl;

import jakarta.servlet.http.HttpSession;



@Controller
public class CartController {

	@Autowired
	private CartServiceImpl cartimpl;
	
	@Autowired
	private CustomerServiceImpl csimpl;
	
	Cart cart;
	boolean flag;
	
	@PostMapping("/cart/add")
	public String addToCart(@RequestParam("foodId") int foodId, HttpSession session,RedirectAttributes ra) {
	    String email = (String) session.getAttribute("userEmail");
	    
	    if (email == null) {
	        return "redirect:/login"; 
	    }

	    Cart addedCart = cartimpl.addToCart(foodId, 1, email);

	    if (addedCart != null) {
	    	ra.addFlashAttribute("successMsg","Item Added Into Cart");
	        return "redirect:/food/list"; 
	    } else {
	    	ra.addFlashAttribute("errMsg","Internal Error .. Item Not added into cart");
	        return "redirect:/food/list";
	    }
	}
	
	@GetMapping("/cart/view")
	public String viewCart(Model model, HttpSession session) {
	    
	    String email = (String) session.getAttribute("userEmail");

	    if (email == null) {
	        return "redirect:/login"; 
	    }

	    
	    Customer customer = csimpl.searchByEmailId(email);

	    if (customer != null) {
	       
	        List<Cart> cartList = cartimpl.searchCartByEmailId(customer.getEmailId());
	        model.addAttribute("cartList", cartList);
	    } else {
	      
	        model.addAttribute("cartList", new ArrayList<Cart>());
	        
	    }

	    return "cartList";
	}
	
	 @PostMapping("/cart/update")
	    @ResponseBody
	    public double updateCartQuantity(@RequestParam int cartid, @RequestParam int quantity) {
	        Cart updatedCart = cartimpl.updateQuantity(cartid, quantity);
	        return updatedCart.getTotalPrice(); 
	    }
	 
	 @GetMapping("/cart/remove/{id}")
	 public String deleteItem(@PathVariable int id , RedirectAttributes ra) {
		 flag=cartimpl.deleteCartById(id);
		 
		 if(flag) {
			 ra.addFlashAttribute("successMsg","Item Removed From cart");
			 return "redirect:/cart/view";
		 }
		 else {
			 ra.addFlashAttribute("errMsg","Item Not Removed");
			 return "redirect:/cart/view";
		 }
		 
	 }
	 
	 @PostMapping("/cart/clear")
	 public String clearCart(Model model, HttpSession session) {
	     String email = (String) session.getAttribute("userEmail");

	     if (email != null) {
	         cartimpl.deleteCartByEmail(email);
	     }

	     
	     return "redirect:/cart/view";
	 }


}
