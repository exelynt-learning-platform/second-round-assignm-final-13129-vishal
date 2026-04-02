package com.ecommerce.project.service;
 

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

 
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public String pay(Double amount) {
        return "Payment Successful (Dummy)";
    }
}






//
//@Service
//public class PaymentService {
//	
//	 
//
//    public String pay(int amount) {
//        return "Payment Successful (Dummy)";
//    }
//
//    static {
//        Stripe.apiKey = "your_secret_key"; // 🔥 Replace with your key
//    }
//
//    public PaymentIntent createPayment(Double amount) throws Exception {
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", (int) (amount * 100)); // in paisa
//        params.put("currency", "inr");
//
//        return PaymentIntent.create(params);
//    }
//}