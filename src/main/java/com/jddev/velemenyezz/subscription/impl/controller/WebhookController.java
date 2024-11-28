package com.jddev.velemenyezz.subscription.impl.controller;

import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.subscription.impl.service.StripeWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe/webhook")
public class WebhookController {

    private final StripeWebhookService stripeWebhookService;

    public WebhookController(StripeWebhookService stripeWebhookService) {
        this.stripeWebhookService = stripeWebhookService;
    }

    @PostMapping
    public ResponseEntity<?> handleStripeWebhook(@RequestBody String payload, @RequestHeader("stripe-signature") String signature)
    {
        APIResponseObject apiResponse = stripeWebhookService.handleWebhook(payload, signature);

        return apiResponse.buildResponse();
    }
}
