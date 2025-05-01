package com.WebGenerator.App.api.controller;

import com.google.gson.JsonObject;
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

    @Value("${stripe.product.priceId}")
    private String priceId;

    @Value("${server.port}")
    private int serverPort;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession() throws Exception {
        // Inicializa Stripe
        Stripe.apiKey = stripeSecretKey;

        // Monta domínio dinâmico: http://localhost:{server.port}
        String domain = "https://website-generator-backend-g3xs.onrender.com";

        // Cria parâmetros da sessão com UI Custom
        SessionCreateParams params = SessionCreateParams.builder()
                .setUiMode(SessionCreateParams.UiMode.CUSTOM)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPrice(priceId)
                        .build())
                .build();

        // Cria sessão e extrai client_secret
        Session session = Session.create(params);
        JsonObject raw = session.getRawJsonObject();
        String clientSecret = raw.getAsJsonPrimitive("client_secret").getAsString();

        // Monta resposta
        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", clientSecret);
        return response;
    }

    @GetMapping("/session-status")
    public Map<String, String> getSessionStatus(@RequestParam("session_id") String sessionId) throws Exception {
        // Inicializa Stripe
        Stripe.apiKey = stripeSecretKey;

        // Recupera sessão
        Session session = Session.retrieve(sessionId);
        JsonObject raw = session.getRawJsonObject();

        // Monta resposta com status e email (se existir)
        Map<String, String> result = new HashMap<>();
        result.put("status", raw.getAsJsonPrimitive("status").getAsString());

        JsonObject customerDetails = raw.getAsJsonObject("customer_details");
        if (customerDetails != null && customerDetails.has("email")) {
            result.put("customer_email", customerDetails.getAsJsonPrimitive("email").getAsString());
        }

        return result;
    }
}
