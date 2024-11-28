package com.jddev.velemenyezz.subscription.impl.events;

import com.jddev.velemenyezz.shared.events.ValidateSubscriptionEvent;
import com.jddev.velemenyezz.subscription.impl.service.SubscriptionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionEventListener {

    private final SubscriptionService subscriptionService;
    public SubscriptionEventListener( SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    @EventListener
    private void SubscriptionValidationListener(ValidateSubscriptionEvent event){
        subscriptionService.validateActiveSubscription(event.userEmail());
    }
}
