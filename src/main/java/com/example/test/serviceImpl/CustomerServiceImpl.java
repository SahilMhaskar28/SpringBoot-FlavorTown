package com.example.test.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.Customer;
import com.example.test.model.Food;
import com.example.test.repository.CustomerRepo;
import com.example.test.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo custRepo;
	
	
	Customer customer;
	boolean flag;
	
	@Override
	public boolean addCustomer(Customer c) {
        Optional<Customer> existing = custRepo.findByEmailId(c.getEmailId());
        
        if (existing.isPresent()) {
            return false;
        }
        else {
        	
        	custRepo.save(c);
            return true;
        }
        
    }

	@Override
	public boolean deleteCustomer(int id) {
		try {
	    custRepo.deleteById(id);
	    return true;
		}
		catch(Exception e) {
			return false;
		}
	    
	}


	@Override
	public boolean updateCustomer(Customer c1) {
		try {
	        if(custRepo.existsById(c1.getCust_id())) {
	            custRepo.save(c1);  
	            return true;
	        } else {
	            return false;
	        }
	    } catch (Exception e) {
	        return false;
	    }
	}

	@Override
	public Customer searchByEmailId(String emailId) {
		customer=custRepo.findByEmailId(emailId).orElse(null);
		
		if(customer != null) {
			return customer;
		}
		else {
			return null;
		}
		
	}

	@Override
	public List<Customer> getAllCustomer() {
		 List<Customer> li = custRepo.findAll();
		 
		 if(li != null) {
			 return li;
		 }
		 else {
			 return null;
		 }
		
	}

	@Override
	public Customer searchById(int id) {
		Optional<Customer> opt = custRepo.findById(id);
		if(!opt.isEmpty()){
			customer=opt.get();
			return customer;
		}
		else {
			return null;
		}
	    
	}
}