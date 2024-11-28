package com.jddev.velemenyezz.subscription.impl.dto.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SubscriptionCompletedPayload {
    String customerEmail;
    String subscriptionID;
    String eventID;
    SubscriptionType subscriptionType;

    LocalDateTime paidAt;
    LocalDateTime expiresAt;

    private ObjectMapper mapper;
    public SubscriptionCompletedPayload(ObjectMapper mapper, String payload) throws JsonProcessingException {
       this.mapper = mapper;
       setPayloadData(payload);
    }

    private void setPayloadData(String payload) throws JsonProcessingException {
        // Convert the payload to json
        JsonNode jsonNode = mapper.readTree(payload);
        // Map the necessary fields
        setCustomerEmail(jsonNode.get("data").get("object").get("customer_email").asText());
        setSubscriptionID(jsonNode.get("data").get("object").get("subscription").asText());
        setEventID(jsonNode.get("id").asText());
        setSubscriptionType(SubscriptionType.valueOf(jsonNode.get("data").get("object").get("metadata").get("subscription_type").asText()));
        // convert and set timestamps
        Instant instant = Instant.ofEpochSecond(jsonNode.get("data").get("object").get("created").asLong());
        LocalDateTime paidAtDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        setPaidAt(paidAtDateTime);
        setExpiresAt(paidAtDateTime.plusMonths(1));

    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
