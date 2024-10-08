package externalServices.stripe_service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.HashMap;
import java.util.Map;

public class StripeService {

    public StripeService(String apiKey) {
        Stripe.apiKey = apiKey;
    }

    public Customer createCustomer(String email, String token) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)
                .setSource(token)
                .build();

        return Customer.create(params);
    }

    public Charge createCharge(String customerId, long amount, String currency) throws StripeException {
        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setCustomer(customerId)
                .build();

        return Charge.create(params);
    }

    public PaymentIntent createPaymentIntent(long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();

        return PaymentIntent.create(params);
    }

    public Customer getCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }

    public Charge getCharge(String chargeId) throws StripeException {
        return Charge.retrieve(chargeId);
    }

    public PaymentIntent getPaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }
}

