package com.ecommerce.project.service;
 

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.project.dto.AuthRequest;
import com.ecommerce.project.entity.Role;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    // REGISTER
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        repo.save(user);
    }

    // LOGIN
    public String login(AuthRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(req.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(req.getEmail());
        }

        throw new RuntimeException("Invalid Email or Password");
    }
    }
