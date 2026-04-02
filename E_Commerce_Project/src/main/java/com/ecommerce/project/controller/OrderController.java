package com.ecommerce.project.controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;
    
    @Autowired
    private UserRepository userRepository;

    // PLACE ORDER
    @PostMapping
    public Order placeOrder(@RequestParam String address, Authentication auth) {

        String email = (String) auth.getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return service.placeOrder(user, address);
    }

    // GET ORDERS
    @GetMapping
    public List<Order> getOrders(Authentication auth) {
    	String email = (String) auth.getPrincipal();

    	User user = userRepository.findByEmail(email)
    	        .orElseThrow(() -> new RuntimeException("User not found"));
        return service.getOrders(user);
    }
}
