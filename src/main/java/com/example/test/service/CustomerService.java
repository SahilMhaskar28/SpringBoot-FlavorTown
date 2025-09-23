package com.example.test.service;

import java.util.List;

import com.example.test.model.Customer;

public interface CustomerService {

	boolean addCustomer(Customer c);
	
	boolean deleteCustomer(int id);
	
	boolean updateCustomer(Customer c1);
	
	Customer searchById(int id);
	
	Customer searchByEmailId(String emailId);
	
	List<Customer> getAllCustomer();
}
