package externalServices.stripe_service.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import externalServices.stripe_service.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final StripeService stripeService;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Autowired
    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-subscription")
    public ResponseEntity<?> createSubscription(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        try {
            String successUrl = "http://localhost:8080/success";
            String cancelUrl = "http://localhost:8080/cancel";
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

    @PostMapping("/webhook")
    public ResponseEntity<?> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            // Handle the event
            if ("checkout.session.completed".equals(event.getType())) {
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                Optional<StripeObject> optionalSession = dataObjectDeserializer.getObject();

                if (optionalSession.isPresent()) {
                    Session session = (Session) optionalSession.get();
                    handleSuccessfulPayment(session);
                } else {
                    System.out.println("Deserialization failed for checkout.session.completed event");
                }
            } else {
                System.out.println("Unhandled event type: " + event.getType());
            }

            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
    }

    private void handleSuccessfulPayment(Session session) {
        // Retrieve the session and perform any necessary actions
        // (e.g., update your database, send a confirmation email, etc.)
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

    @PostMapping("/cancel-subscription")
    public ResponseEntity<?> cancelSubscription(@RequestBody Map<String, String> payload) {
        String subscriptionId = payload.get("subscriptionId");

        try {
            Subscription canceledSubscription = stripeService.cancelSubscription(subscriptionId);
            return ResponseEntity.ok("Subscription canceled successfully");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling subscription: " + e.getMessage());
        }
    }
}


