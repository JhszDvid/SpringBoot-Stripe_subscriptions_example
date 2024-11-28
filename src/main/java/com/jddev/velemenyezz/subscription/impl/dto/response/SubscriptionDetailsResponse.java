package com.jddev.velemenyezz.subscription.impl.dto.response;

import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionStatus;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;
import com.jddev.velemenyezz.subscription.impl.model.Subscription;

public class SubscriptionDetailsResponse {
    public SubscriptionStatus subscriptionStatus;
    public SubscriptionType subscriptionType;
    public SubscriptionDetailsResponse(SubscriptionStatus status, SubscriptionType type){
        subscriptionStatus = status;
        subscriptionType = type;
    }

}
