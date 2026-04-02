package com.ecommerce.project.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // 🔐 FIXED SECRET (must be >= 256 bits)
//    private static final String SECRET_STRING = "mysecretkeymysecretkeymysecretkey123456"; // 32+ chars

    @Value("${jwt.secret}")
    private String secret;
    
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            Base64.getEncoder().encode(secret.getBytes())
    );

    // 🔐 Generate Token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 🔍 Extract Email
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}