package com.WebGenerator.App.api.controller;

import com.WebGenerator.App.api.dto.PixPaymentDto;
import com.google.gson.JsonObject;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.core.MPRequestOptions;


import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> payload) throws Exception {
        Stripe.apiKey = stripeSecretKey;
        String domain = "https://website-generator-backend-g3xs.onrender.com";

        Long amount = Long.parseLong(payload.get("amount").toString());
        String currency = payload.get("currency").toString();
        String productName = payload.get("name").toString();

        SessionCreateParams params = SessionCreateParams.builder()
                .setUiMode(SessionCreateParams.UiMode.CUSTOM)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setReturnUrl(domain + "/return?session_id={CHECKOUT_SESSION_ID}")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(currency)
                                        .setUnitAmount(amount)
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(productName)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build())
                .build();

        Session session = Session.create(params);
        JsonObject raw = session.getRawJsonObject();
        String clientSecret = raw.getAsJsonPrimitive("client_secret").getAsString();

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", clientSecret);
        return response;
    }
    @GetMapping("/session-status")
    public Map<String, String> getSessionStatus(@RequestParam("session_id") String sessionId) throws Exception {
        Stripe.apiKey = stripeSecretKey;

        Session session = Session.retrieve(sessionId);
        JsonObject raw = session.getRawJsonObject();

        Map<String, String> result = new HashMap<>();
        result.put("status", raw.getAsJsonPrimitive("status").getAsString());

        JsonObject customerDetails = raw.getAsJsonObject("customer_details");
        if (customerDetails != null && customerDetails.has("email")) {
            result.put("customer_email", customerDetails.getAsJsonPrimitive("email").getAsString());
        }

        return result;
    }




    @PostMapping("/create-pix-payment")
    public Map<String, Object> createPixPayment(
            @RequestBody PixPaymentDto req
    ) throws Exception {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);


        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());
        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();


        OffsetDateTime expiration = OffsetDateTime
                .now(ZoneOffset.UTC)
                .plusHours(24);


        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(req.transactionAmount)
                .description(req.description)
                .paymentMethodId("pix")
                .dateOfExpiration(expiration)
                .payer(
                        PaymentPayerRequest.builder()
                                .email(req.email)
                                .firstName(req.payerFirstName)
                                .build()
                )
                .build();


        PaymentClient client = new PaymentClient();
        Payment payment = client.create(paymentCreateRequest, requestOptions);

        Map<String, Object> response = new HashMap<>();
        response.put("id", payment.getId());
        response.put("status", payment.getStatus());
        response.put("qrCode", payment.getPointOfInteraction().getTransactionData().getQrCode());
        response.put("qrCodeBase64", payment.getPointOfInteraction().getTransactionData().getQrCodeBase64());
        return response;
    }
    @GetMapping("/check-pix-status")
    public Map<String, Object> checkPixStatus(@RequestParam("id") Long paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);

        Map<String, Object> response = new HashMap<>();
        response.put("id", payment.getId());
        response.put("status", payment.getStatus()); // Ex: pending, approved, rejected
        response.put("statusDetail", payment.getStatusDetail());

        return response;
    }

}
