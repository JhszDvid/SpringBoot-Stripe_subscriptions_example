package com.jddev.velemenyezz.subscription.impl.dto.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SubscriptionDeletedPayload {

    String subscriptionID;
    String eventID;

    private ObjectMapper mapper;
    public SubscriptionDeletedPayload(ObjectMapper mapper, String payload) throws JsonProcessingException {
       this.mapper = mapper;
       setPayloadData(payload);
    }

    private void setPayloadData(String payload) throws JsonProcessingException {
        // Convert the payload to json
        JsonNode jsonNode = mapper.readTree(payload);
        // Map the necessary fields
        setSubscriptionID(jsonNode.get("data").get("object").get("id").asText());
        setEventID(jsonNode.get("id").asText());

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

}
