// In OrderServiceImpl.java

package com.example.test.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.OrderFood;
import com.example.test.repository.OrderRepository;
import com.example.test.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartServiceImpl cartimpl;

    @Override
    public boolean placeOrder(OrderFood o) {
        try {
            orderRepo.save(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderFood showOrderById(int orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderFood> showAllOrder() {
        return orderRepo.findAll();
    }

    @Override
    public boolean changeOrderStatus(String orderStatus, int orderId) {
        // Find the order by its ID.
        Optional<OrderFood> orderOptional = orderRepo.findById(orderId);
        
        // Check if the order exists.
        if (orderOptional.isPresent()) {
            OrderFood order = orderOptional.get();
            // Update the status and save the entity.
            order.setOrderStatus(orderStatus);
            orderRepo.save(order);
            // Return true to indicate a successful update.
            return true;
        }
        // Return false if the order was not found.
        return false;
    }

    @Override
    public List<OrderFood> showMyOrderHistory(String emailId) {
    	 return orderRepo.findByEmailId(emailId);
    }
}