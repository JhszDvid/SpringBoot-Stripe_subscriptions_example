package com.jddev.velemenyezz.subscription.impl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import com.jddev.velemenyezz.subscription.impl.dto.response.SubscriptionDetailsResponse;
import com.jddev.velemenyezz.subscription.impl.dto.stripe.SubscriptionCompletedPayload;
import com.jddev.velemenyezz.subscription.impl.dto.stripe.SubscriptionDeletedPayload;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionStatus;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.StripeWebhookProcessingException;
import com.jddev.velemenyezz.subscription.impl.model.Subscription;
import com.jddev.velemenyezz.subscription.impl.repository.SubscriptionRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StripeWebhookService {

    Logger logger = LoggerFactory.getLogger(StripeWebhookService.class);
    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    private final ObjectMapper mapper;

    private final SubscriptionRepository subscriptionRepository;

    public StripeWebhookService(ObjectMapper mapper, SubscriptionRepository subscriptionRepository) {
        this.mapper = mapper;
        this.subscriptionRepository = subscriptionRepository;
    }


    private void saveActiveSubscription(SubscriptionCompletedPayload payload){
        logger.info("saveActiveSubscription STARTED");
        Optional<Subscription> subscription = subscriptionRepository.findById(payload.getCustomerEmail());
        Subscription updatedSubscription;
        if(subscription.isPresent()) {
            logger.info("update subscription, ID: " + subscription.get().getSubscriptionID());
            // update current subscription
            updatedSubscription = subscription.get();
            updatedSubscription.setSubscriptionType(payload.getSubscriptionType());
            updatedSubscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
            updatedSubscription.setEventID(payload.getEventID());
            updatedSubscription.setPaidAt(payload.getPaidAt());
            updatedSubscription.setExpiresAt(payload.getExpiresAt());
            updatedSubscription.setSubscriptionID(payload.getSubscriptionID());
        }
        else{
            logger.info("create subscription for customer: " + payload.getCustomerEmail());
            updatedSubscription = new Subscription.Builder()
                    .withSubscriptionType(payload.getSubscriptionType())
                    .withSubscriptionStatus(SubscriptionStatus.ACTIVE)
                    .withEventID(payload.getEventID())
                    .withPaidAt(payload.getPaidAt())
                    .withExpiresAt(payload.getExpiresAt())
                    .withOwnerEmail(payload.getCustomerEmail())
                    .withSubscriptionID(payload.getSubscriptionID())
                    .build();
        }


        logger.info("saving updated subscription...");
        subscriptionRepository.save(updatedSubscription);
        logger.info("saveActiveSubscription FINISHED");
    }

    private void handleCancellationWebhook(SubscriptionDeletedPayload payload){
        logger.info("handleCancellationWebhook STARTED");
        Optional<Subscription> subscription = subscriptionRepository.findBySubscriptionID(payload.getSubscriptionID());
        if(subscription.isEmpty()){
            logger.info("could not find a subscription with this ID, exiting flow");
            return;
        }
        logger.info("updating subscription to inactive, subscription ID: " + subscription.get().getSubscriptionID());
        Subscription subscriptionToCancel = subscription.get();
        subscriptionToCancel.setExpiresAt(LocalDateTime.now());
        subscriptionToCancel.setSubscriptionStatus(SubscriptionStatus.INACTIVE);
        subscriptionToCancel.setSubscriptionType(SubscriptionType.NONE);
        subscriptionToCancel.setEventID(payload.getEventID());
        subscriptionToCancel.setSubscriptionID(payload.getSubscriptionID());

        logger.info("saving updated subscription...");
        subscriptionRepository.save(subscriptionToCancel);

        logger.info("handleCancellationWebhook FINISHED");
    }


    public APIResponseObject handleWebhook(String payload, String signature)
    {
        logger.info("handleWebhook STARTED");
        Event event;
        try{
            logger.info("constructing stripe event...");
            event = Webhook.constructEvent(payload, signature, webhookSecret);
        } catch(SignatureVerificationException e) {
            throw new StripeWebhookProcessingException(e.getMessage());
        }

        try {
            String eventType = event.getType();
            logger.info("event type: " + eventType);
            switch (eventType) {
                case "checkout.session.completed" -> {
                    logger.info("parsing payload...");
                    SubscriptionCompletedPayload stripePayload = new SubscriptionCompletedPayload(mapper, payload);
                    saveActiveSubscription(stripePayload);
                }
                case "customer.subscription.deleted" -> {
                    logger.info("parsing payload...");
                    SubscriptionDeletedPayload stripePayload = new SubscriptionDeletedPayload(mapper, payload);
                    handleCancellationWebhook(stripePayload);
                }
                default -> {
                    // not handled event...
                }
            }

        } catch (Exception e) {
            throw new StripeWebhookProcessingException(e.getMessage());
        }

        logger.info("handleWebhook FINISHED");
        return new APIResponseObject.Builder()
                .withMessage("Successfully processed webhook")
                .withResponseType(ResponseType.SUCCESS)
                .withStatusCode(HttpStatus.OK)
                .build();
    }
}
