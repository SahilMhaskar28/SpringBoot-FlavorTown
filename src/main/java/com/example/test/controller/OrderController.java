package com.example.test.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import com.example.test.model.Cart;
import com.example.test.model.Customer;
import com.example.test.model.OrderFood;
import com.example.test.serviceImpl.CartServiceImpl;
import com.example.test.serviceImpl.CustomerServiceImpl;
import com.example.test.serviceImpl.OrderServiceImpl;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl odimpl;

    @Autowired
    private CartServiceImpl cartimpl;
    
    @Autowired
    private CustomerServiceImpl csimpl;
    
    @PostMapping("/place")
    public String placeOrder(HttpSession session) {
        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        // Get all items from the cart to calculate the total bill
        List<Cart> cartItems = cartimpl.searchCartByEmailId(email);

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart/view";
        }

        double totalBill = 0.0;
        for (Cart item : cartItems) {
            totalBill += item.getTotalPrice();
        }

        // Create a single OrderFood object for the entire order
        OrderFood order = new OrderFood();
        order.setEmailId(email);
        order.setTotalBill(totalBill);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Pending");

        boolean isPlaced = odimpl.placeOrder(order);

        if (isPlaced) {

            return "orderPlaced";
        } else {
            return "redirect:/cart/view";
        }
    }

    @GetMapping("/history")
    public String viewOrderHistory(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/login";
        }
        List<OrderFood> orders = odimpl.showMyOrderHistory(email);
        model.addAttribute("orderList", orders);
        return "orderHistory";
    }
    
    @GetMapping("/all")
    public String viewAllOrders(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/"; // Redirect unauthorized users
        }

        List<OrderFood> allOrders = odimpl.showAllOrder();
        model.addAttribute("orderList", allOrders);
        return "allOrders";
    }
    
    @GetMapping("/details/{orderId}")
    public String viewOrderDetails(@PathVariable int orderId, Model model) {
        OrderFood order = odimpl.showOrderById(orderId);
        if (order == null) {
            return "redirect:/order/history"; 
        }
        
        Customer customer = csimpl.searchByEmailId(order.getEmailId());

        model.addAttribute("order", order);
        model.addAttribute("customer", customer);

        List<Cart> orderedItems = cartimpl.searchCartByEmailId(order.getEmailId());
        
        model.addAttribute("orderedItems", orderedItems);

        return "orderDetails";
    }
    

    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") int orderId, @RequestParam("newStatus") String newStatus, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/";
        }
        boolean updated = odimpl.changeOrderStatus(newStatus, orderId);
        if (updated) {
            return "redirect:/order/all?status=success";
        } else {
            return "redirect:/order/all?status=error";
        }
    }
}