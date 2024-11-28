package com.jddev.velemenyezz.subscription.impl.controller;

import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.subscription.impl.dto.response.SubscriptionDetailsResponse;
import com.jddev.velemenyezz.subscription.impl.service.SubscriptionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;
    private final SharedMethods sharedMethods;
    public SubscriptionController(SubscriptionServiceImpl subscriptionServiceImpl, SharedMethods sharedMethods) {
        this.subscriptionService = subscriptionServiceImpl;
        this.sharedMethods = sharedMethods;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getSubscriptionStatus()
    {
        String userId = sharedMethods.getAuthenticatedUserEmail();
        SubscriptionDetailsResponse response = subscriptionService.getSubscriptionDetails(userId);
        return new APIResponseObject.Builder()
                .withObject(response)
                .buildResponse();
    }
    @GetMapping
    public String getPaymentUrl(){
        return subscriptionService.createStripeSession();
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelSubscription()
    {
        String userId = sharedMethods.getAuthenticatedUserEmail();
        return subscriptionService.cancelSubscription(userId).buildResponse();
    }


}
