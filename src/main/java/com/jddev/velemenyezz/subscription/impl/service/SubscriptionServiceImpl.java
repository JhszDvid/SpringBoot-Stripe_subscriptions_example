package com.jddev.velemenyezz.subscription.impl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.shared.exception.InternalErrorException;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import com.jddev.velemenyezz.subscription.impl.dto.stripe.SubscriptionCompletedPayload;
import com.jddev.velemenyezz.subscription.impl.dto.response.SubscriptionDetailsResponse;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionStatus;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.AlreadySubscribedException;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.InactiveSubscriptionException;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.StripeWebhookProcessingException;
import com.jddev.velemenyezz.subscription.impl.model.Subscription;
import com.jddev.velemenyezz.subscription.impl.repository.SubscriptionRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;

import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.SubscriptionCancelParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;




@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${stripe.session.priceId}")
    private String priceId;

    @Value("${stripe.secret-key}")
    private String stripeApiKey;

    Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
    private final SharedMethods sharedMethods;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SharedMethods sharedMethods, SubscriptionRepository subscriptionRepository) {

        this.sharedMethods = sharedMethods;
        this.subscriptionRepository = subscriptionRepository;
    }
    @Override
    public void validateActiveSubscription(String userID){
        Subscription subscription = subscriptionRepository.findById(userID).orElseThrow(() -> new InactiveSubscriptionException("Could not find an active subscription!"));
        if(subscription.getSubscriptionStatus().equals(SubscriptionStatus.INACTIVE))
            throw new InactiveSubscriptionException("Could not find an active subscription!");
    }

    @Override
    public SubscriptionDetailsResponse getSubscriptionDetails(String userID) {
        Optional<Subscription> subscription = subscriptionRepository.findById(userID);
        return subscription.map(value -> new SubscriptionDetailsResponse(value.getSubscriptionStatus(), value.getSubscriptionType()))
                .orElseGet(() -> new SubscriptionDetailsResponse(SubscriptionStatus.INACTIVE, SubscriptionType.NONE));
    }

    @Override
    public APIResponseObject cancelSubscription(String userID) {
        logger.info("cancelSubscription started for userID: " + userID);
        Optional<Subscription> subscription = subscriptionRepository.findById(userID);
        if(subscription.isEmpty() || subscription.get().getSubscriptionStatus() == SubscriptionStatus.INACTIVE) {
            logger.info("subscription was not found for this user, exiting...");
            return new APIResponseObject.Builder()
                    .withMessage("Could not find an active subscription!")
                    .withResponseType(ResponseType.ERROR)
                    .withStatusCode(HttpStatus.NOT_FOUND)
                    .build();
        }


        try{
            Stripe.apiKey = stripeApiKey;
            logger.info("retrieving subscription from stripe, subscription ID: " + subscription.get().getSubscriptionID());
            com.stripe.model.Subscription stripeSubscription = com.stripe.model.Subscription.retrieve(subscription.get().getSubscriptionID());
            SubscriptionCancelParams subscriptionCancelParams = new SubscriptionCancelParams.Builder().build();
            logger.info("cancelling subscription...");
            stripeSubscription.cancel(subscriptionCancelParams);
        }
        catch (StripeException e) {
            throw new InternalErrorException("stripe error: " + e.getMessage());
        }
        logger.info("cancelSubscription FINISHED");
        return new APIResponseObject.Builder().build();
    }

    @Override
    public boolean hasActiveSubscription(String userID){
        Optional<Subscription> subscription = subscriptionRepository.findById(userID);
        return subscription.filter(value -> value.getSubscriptionStatus() == SubscriptionStatus.ACTIVE).isPresent();
    }

    @Override
    public String createStripeSession(){
        logger.info("createStripeSession STARTED");
        String userEmail = sharedMethods.getAuthenticatedUserEmail();
       if(hasActiveSubscription(userEmail)){
           logger.info("user already has an active subscription, exiting flow...");
           throw new AlreadySubscribedException("The user is already subscribed to the service! Please unsubscribe first");
       }

        Stripe.apiKey = stripeApiKey;
        try{
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setSuccessUrl("http://localhost:5173/dashboard")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPrice(priceId)
                                            .setQuantity(1L)
                                            .build()
                            )
                            .putMetadata("subscription_type", "STANDARD")
                            .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                            .setTaxIdCollection(SessionCreateParams.TaxIdCollection.builder().setEnabled(true).build())
                            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                            .setCustomerEmail(userEmail)
                            .build();
            Session session = Session.create(params);
            return session.getUrl();
        }
        catch(StripeException e) {
            throw new StripeWebhookProcessingException(e.getMessage());
        }

    }


}
