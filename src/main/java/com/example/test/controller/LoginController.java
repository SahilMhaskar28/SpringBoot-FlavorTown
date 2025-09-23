package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.model.Admin;
import com.example.test.model.Customer;
import com.example.test.serviceImpl.AdminServiceImpl;
import com.example.test.serviceImpl.CustomerServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private CustomerServiceImpl csimpl;

	@Autowired
	private AdminServiceImpl asimpl;

	@GetMapping("/login")
	public String login(Model model) {
		Customer c = new Customer();
		model.addAttribute("loginObj", c);
		return "login";
	}

	@PostMapping("/login/check")
	public String checkLogin(@RequestParam String email, @RequestParam String password, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

		
		Customer customer = csimpl.searchByEmailId(email);
		if (customer != null && customer.getCustPassword().equals(password)) {
			
			session.setAttribute("userEmail", customer.getEmailId());
			session.setAttribute("userRole", customer.getRole());
			redirectAttributes.addFlashAttribute("successMsg", "Customer login successful!");
			return "redirect:/";
		}

		
		Admin admin = asimpl.searchByEmailId(email); 
		if (admin != null && admin.getPassword().equals(password)) {
			
			session.setAttribute("userEmail", admin.getUserName()); 
			session.setAttribute("userRole", admin.getRole());
			redirectAttributes.addFlashAttribute("successMsg", "Admin login successful!");
			return "redirect:/";
		}

		
		model.addAttribute("loginError", "Invalid email or password.");
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.invalidate(); 
		redirectAttributes.addFlashAttribute("successMsg", "You have been logged out.");
		return "redirect:/";
	}
}