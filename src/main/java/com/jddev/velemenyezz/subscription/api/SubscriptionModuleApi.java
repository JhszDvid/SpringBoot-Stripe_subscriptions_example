package com.jddev.velemenyezz.subscription.api;

import com.jddev.velemenyezz.subscription.impl.model.Subscription;
import com.jddev.velemenyezz.subscription.impl.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionModuleApi {

    private final SubscriptionService subscriptionService;

    public SubscriptionModuleApi(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public boolean hasActiveSubscription(String userID) {
        return subscriptionService.hasActiveSubscription(userID);
    }

    public void validateActiveSubscription(String userId) {
        subscriptionService.validateActiveSubscription(userId);
    }
}
