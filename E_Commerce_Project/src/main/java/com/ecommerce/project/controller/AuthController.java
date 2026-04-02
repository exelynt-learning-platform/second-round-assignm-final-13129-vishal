package com.ecommerce.project.controller;
 
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.project.dto.AuthRequest;
import com.ecommerce.project.dto.AuthResponse;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        service.register(user);
        return "User Registered Successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        String token = service.login(req);
        return new AuthResponse(token);
    }
}