package com.ecommerce.project.controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;
    
	@Value("${stripe.webhook.secret}")
	private String endpointSecret;
   
	@PostMapping("/create")
	public ResponseEntity<?> createPayment(@RequestParam Double amount,
	                                       @RequestParam Long orderId) {
	    try {
	        PaymentIntent intent = paymentService.createPayment(amount, orderId);
	        return ResponseEntity.ok(intent.getClientSecret());
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
	    }
	}

	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebhook(
	        @RequestBody String payload,
	        @RequestHeader("Stripe-Signature") String sigHeader) {

	    Event event;

	    try {
	        //  STRICT signature verification
	        event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
	    } catch (SignatureVerificationException e) {
	        // ❌ Invalid signature → possible attack
	        return ResponseEntity.status(400).body("Invalid signature");
	    } catch (Exception e) {
	        return ResponseEntity.status(400).body("Webhook error");
	    }

	    //  Only allow specific events
	    String eventType = event.getType();

	    if (!eventType.equals("payment_intent.succeeded") &&
	        !eventType.equals("payment_intent.payment_failed")) {

	        return ResponseEntity.ok("Event ignored");
	    }

	    try {
	        System.out.println("Stripe event: " + eventType);

	        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
	                .getObject().orElse(null);

	        if (intent == null) {
	            return ResponseEntity.badRequest().body("Invalid payment data");
	        }

	        String orderIdStr = intent.getMetadata().get("orderId");

	        if (orderIdStr == null) {
	            return ResponseEntity.badRequest().body("Order ID missing");
	        }

	        Long orderId = Long.parseLong(orderIdStr);

	        if ("payment_intent.succeeded".equals(eventType)) {
	            orderService.updatePaymentStatus(orderId, "PAID");
	        } else if ("payment_intent.payment_failed".equals(eventType)) {
	            orderService.updatePaymentStatus(orderId, "FAILED");
	        }

	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Processing error");
	    }

	    return ResponseEntity.ok("Success");
	}	
}