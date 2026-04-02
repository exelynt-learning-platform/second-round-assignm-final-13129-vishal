package com.ecommerce.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    public PaymentIntent createPayment(Double amount, Long orderId) {

        // Validation
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        if (orderId == null) {
            throw new IllegalArgumentException("Order ID is required");
        }

        try {
            Map<String, Object> params = new HashMap<>();

            long amountInPaisa = Math.round(amount * 100);

            params.put("amount", amountInPaisa);
            params.put("currency", "inr");
            params.put("payment_method_types", java.util.List.of("card"));

            // Metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("orderId", String.valueOf(orderId));

            params.put("metadata", metadata);

            params.put("description", "Payment for Order ID: " + orderId);

            return PaymentIntent.create(params);

        } catch (Exception e) {
            throw new RuntimeException("Payment creation failed", e);
        }
    }
   
}