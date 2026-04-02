package com.ecommerce.project.service;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderItem;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.OrderRepository;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private OrderRepository orderRepo;

    // PLACE ORDER FROM CART
    public Order placeOrder(User user, String address) {

        List<CartItem> cartItems = cartRepo.findByUserId(user.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cart : cartItems) {

            OrderItem item = new OrderItem();
            item.setProductName(cart.getProduct().getName());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getProduct().getPrice());

            total += cart.getQuantity() * cart.getProduct().getPrice();

            orderItems.add(item);
        }

        Order order = new Order();
        order.setUser(user);
        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus("CREATED");
        order.setAddress(address);

        cartRepo.deleteAll(cartItems);

        return orderRepo.save(order);
    }

    // GET USER ORDERS
    public List<Order> getOrders(User user) {
        return orderRepo.findByUserId(user.getId());
    }
}