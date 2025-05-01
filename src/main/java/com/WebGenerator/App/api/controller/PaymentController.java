package com.WebGenerator.App.api.controller;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession() throws Exception {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:3000/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice("price_1234567890") // substitua com o ID real do Stripe
                                .build())
                .build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("checkoutUrl", session.getUrl());

        return responseData;
    }

    @GetMapping("/session-status")
    public Map<String, String> getSessionStatus(@RequestParam String session_id) throws Exception {
        Stripe.apiKey = stripeSecretKey;

        Session session = Session.retrieve(session_id);

        Map<String, String> result = new HashMap<>();
        result.put("status", session.getStatus());
        if (session.getCustomerDetails() != null) {
            result.put("customer_email", session.getCustomerDetails().getEmail());
        }

        return result;
    }
}
