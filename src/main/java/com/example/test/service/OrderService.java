package com.example.test.service;

import java.util.List;

import com.example.test.model.OrderFood;

public interface OrderService {
	
	boolean placeOrder(OrderFood o);	
	OrderFood showOrderById(int orderId);
	List<OrderFood> showAllOrder();
	public boolean changeOrderStatus(String orderStatus, int orderId);
	public List<OrderFood> showMyOrderHistory(String emailId);
}
