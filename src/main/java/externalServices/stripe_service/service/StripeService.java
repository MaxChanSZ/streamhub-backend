package externalServices.stripe_service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import externalServices.stripe_service.repository.UserRepository;
import com.fdmgroup.backend_streamhub.authenticate.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @Autowired
    private UserRepository userRepository;

    public StripeService() {
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    public String getCustomerIdByEmail(String email) throws StripeException {
        CustomerListParams params = CustomerListParams.builder()
                .setEmail(email)
                .build();
        CustomerCollection customers = Customer.list(params);

        if (!customers.getData().isEmpty()) {
            return customers.getData().get(0).getId();
        }
        return null;
    }


    public Subscription getSubscription(String subscriptionId) throws StripeException {
        return Subscription.retrieve(subscriptionId);
    }

    public Subscription cancelSubscription(String subscriptionId) throws StripeException {
        Subscription subscription = Subscription.retrieve(subscriptionId);
        SubscriptionCancelParams params = SubscriptionCancelParams.builder().build();
        return subscription.cancel(params);
    }

    public Session createCheckoutSession(String priceId, String successUrl, String cancelUrl) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(priceId)
                                .setQuantity(1L)
                                .build()
                )
                .build();

        return Session.create(params);
    }

    public Session createMonthlySubscription(String email, String successUrl, String cancelUrl) throws StripeException {
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setUnitAmount(499L)  // $4.99 in cents
                .setCurrency("usd")
                .setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                .build()
                )
                .setProduct("prod_R0Zhc0fSEIWByQ")
                .build();
        Price price = Price.create(priceParams);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .setCustomerEmail(email)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build()
                )
                .build();

        return Session.create(params);
    }

    public Subscription getActiveSubscription(String customerId) throws StripeException {
        CustomerRetrieveParams params = CustomerRetrieveParams.builder()
                .addExpand("subscriptions")
                .build();
        Customer customer = Customer.retrieve(customerId, params, null);

        SubscriptionCollection subscriptions = customer.getSubscriptions();
        if (subscriptions != null && !subscriptions.getData().isEmpty()) {
            return subscriptions.getData().get(0);
        }
        return null;
    }

    public String createOrGetCustomer(String email) throws StripeException {
        CustomerListParams params = CustomerListParams.builder()
                .setEmail(email)
                .build();
        CustomerCollection customers = Customer.list(params);

        if (!customers.getData().isEmpty()) {
            return customers.getData().get(0).getId();
        } else {
            CustomerCreateParams createParams = CustomerCreateParams.builder()
                    .setEmail(email)
                    .build();
            Customer newCustomer = Customer.create(createParams);
            return newCustomer.getId();
        }
    }

    public Map<String, Object> getUserInfo(Long id) {
        Optional<Account> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Account user = userOptional.get();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            return userInfo;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<Account> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}





