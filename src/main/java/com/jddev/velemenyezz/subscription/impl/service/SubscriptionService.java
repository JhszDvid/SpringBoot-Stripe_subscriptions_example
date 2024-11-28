package com.jddev.velemenyezz.subscription.impl.service;

import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.subscription.impl.dto.response.SubscriptionDetailsResponse;

public interface SubscriptionService {
    public void validateActiveSubscription(String userID);
    public SubscriptionDetailsResponse getSubscriptionDetails(String userID);
    public APIResponseObject cancelSubscription(String userID);
    public boolean hasActiveSubscription(String userID);
    public String createStripeSession();
}
