package com.ecommerce.project.controller;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;
    @Autowired
    private UserRepository userRepository;

    // ADD ITEM
    @PostMapping
    public CartItem add(@RequestBody CartItem item, Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        item.setUser(user);

        return service.add(item);
    }

    // GET CART
    @GetMapping
    public List<CartItem> get(Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return service.get(user);
    }

    // DELETE ITEM
    @DeleteMapping("/{id}")
    public String remove(@PathVariable Long id, Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        service.remove(id, user);

        return "Item Removed";
    }}