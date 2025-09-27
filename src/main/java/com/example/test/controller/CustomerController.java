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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.model.Customer;
import com.example.test.model.Food;
import com.example.test.serviceImpl.CustomerServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl csimpl;
	boolean flag;
	
	@GetMapping("/customer/add")
	public String addCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "Register";
	}
	
	@PostMapping("/customer/save")
	public String saveCustomer(Customer customer,RedirectAttributes ra) {
		flag = csimpl.addCustomer(customer);
		
		if(flag) {
			ra.addFlashAttribute("successMsg","User Successfully Registered. Login Here");
			return "redirect:/login";
		}
		else {
			ra.addFlashAttribute("errMsg","Registration failed. Try again.");
			return "redirect:/customer/add";
		}		
		
	}
	
	@GetMapping("/customer/list")
	public String getAllCustomer(Model model) {
		List<Customer> li=csimpl.getAllCustomer();
		
		if( li != null) {
			model.addAttribute("cList", li);
			return "customerList";
		}
		else {
			model.addAttribute("errorMsg", "Customer List is Empty");
			return "customerList";
		}
	}
	
	@GetMapping("/customer/update/{id}")
	public String updateCustomer(Model model, @PathVariable int id) {
		Customer c = csimpl.searchById(id);
		
		if(c != null ) {
			model.addAttribute("customer", c);
			return "updateCustomer";		
		}
		else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/customer/update")
	public String saveUpdatedCustomer(Customer customer, RedirectAttributes ra) {
	    flag = csimpl.updateCustomer(customer);
	    
	    if(flag) {
	        ra.addFlashAttribute("successMsg", "User successfully updated");
	    } else {
	        ra.addFlashAttribute("errMsg", "Update failed. Try again.");
	    }
	    return "redirect:/customer/list";
	}
	
	@GetMapping("/customer/delete/{id}")
	public String deleteCustomer(@PathVariable int id,RedirectAttributes ra) {
		
		flag= csimpl.deleteCustomer(id);
		
		if(flag) {
			ra.addFlashAttribute("successMsg","User Successfully deleted");
		}
		else {
			ra.addFlashAttribute("errMsg","User Not deleted .... Try Again");
		}
		return "redirect:/customer/list";
	}
	
	 @GetMapping("/customer/search")
	    public String searchCustomer(@RequestParam String keyword, Model model) {
		 Customer result = csimpl.searchByEmailId(keyword);     
		    
		    List<Customer> customerList = new ArrayList<>();
		    if (result != null) {
		        customerList.add(result);
		    }

		    model.addAttribute("cList", customerList);
		    model.addAttribute("keyword", keyword);
		    
		    return "customerList";
	    }
	
	 @GetMapping("/customer/profile")
	 public String customerProfile(Model model,HttpSession session) {
		   String email = (String) session.getAttribute("userEmail");

		    if (email != null) {
		        Customer customer = csimpl.searchByEmailId(email);
		        model.addAttribute("customer", customer);
		        return "profile";
		    } else {
		        return "redirect:/login";
		    }
	 }
	 
	 @GetMapping("/contact")
	 public String ContactUs() {
		 return "Contact";
	 }
	 
	 @GetMapping("/contactMsg")
	 public String showMSg(RedirectAttributes ra) {
		 ra.addFlashAttribute("successMsg", "Submission was successfull!");
		 return "redirect:/contact";
	 }
	 
}