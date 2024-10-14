package externalServices.stripe_service.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import externalServices.stripe_service.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final StripeService stripeService;

    @Autowired
    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        try {
            Map<String, Object> userInfo = stripeService.getUserInfo(id);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user information: " + e.getMessage());
        }
    }

    @PostMapping("/create-or-get-customer")
    public ResponseEntity<?> createOrGetCustomer(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        try {
            String customerId = stripeService.createOrGetCustomer(email);
            return ResponseEntity.ok(Map.of("customerId", customerId));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating or getting customer: " + e.getMessage());
        }
    }

    @PostMapping("/create-subscription")
    public ResponseEntity<?> createSubscription(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        try {
            String successUrl = "http://localhost:5173/update-profile";
            String cancelUrl = "http://localhost:5173/update-profile";
            Session session = stripeService.createMonthlySubscription(email, successUrl, cancelUrl);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());
            responseData.put("url", session.getUrl());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating subscription: " + e.getMessage());
        }
    }

    @GetMapping("/subscription/status")
    public ResponseEntity<?> getSubscriptionStatus(@RequestParam String email) {
        try {
            String customerId = stripeService.createOrGetCustomer(email);
            Subscription subscription = stripeService.getActiveSubscription(customerId);
            if (subscription != null) {
                Map<String, Object> status = new HashMap<>();
                status.put("status", subscription.getStatus());
                status.put("currentPeriodEnd", subscription.getCurrentPeriodEnd());
                status.put("subscriptionId", subscription.getId());
                return ResponseEntity.ok(status);
            } else {
                return ResponseEntity.ok(Map.of("status", "inactive"));
            }
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching subscription status: " + e.getMessage());
        }
    }


    @PostMapping("/cancel-subscription")
    public ResponseEntity<?> cancelSubscription(@RequestBody Map<String, String> payload) {
        String subscriptionId = payload.get("subscriptionId");

        try {
            Subscription canceledSubscription = stripeService.cancelSubscription(subscriptionId);
            return ResponseEntity.ok(Map.of("status", canceledSubscription.getStatus()));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error canceling subscription: " + e.getMessage());
        }
    }

    private void handleSuccessfulPayment(Session session) {
        String customerId = session.getCustomer();
        String subscriptionId = session.getSubscription();

        try {
            Subscription subscription = stripeService.getSubscription(subscriptionId);
            // Update user's subscription status in your database
            // Send confirmation email
            System.out.println("Subscription activated for customer: " + customerId);
        } catch (StripeException e) {
            System.err.println("Error handling successful payment: " + e.getMessage());
        }
    }
}




