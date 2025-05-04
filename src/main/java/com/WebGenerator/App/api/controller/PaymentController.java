package com.WebGenerator.App.api.controller;

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

    @Value("${stripe.product.priceId}")
    private String priceId;

    @Value("${server.port}")
    private int serverPort;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession() throws Exception {
        Stripe.apiKey = stripeSecretKey;
        String domain = "https://website-generator-backend-g3xs.onrender.com";

        SessionCreateParams params = SessionCreateParams.builder()
                .setUiMode(SessionCreateParams.UiMode.CUSTOM)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setReturnUrl(domain + "/return?session_id={CHECKOUT_SESSION_ID}")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPrice(priceId)
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
    public Map<String, Object> createPixPayment() throws Exception {
        // Configura o access token
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        // Cabeçalho customizado de idempotência
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        OffsetDateTime expiration = OffsetDateTime
                .now(ZoneOffset.UTC)
                .plusHours(24);

        // Criação da requisição de pagamento Pix
        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(new BigDecimal("100"))
                        .description("Título do produto")
                        .paymentMethodId("pix")
                        .dateOfExpiration(expiration)
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email("pagador@email.com")
                                        .firstName("Test")
                                        .identification(
                                                IdentificationRequest.builder()
                                                        .type("CPF")
                                                        .number("19119119100")
                                                        .build())
                                        .build())
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
}
