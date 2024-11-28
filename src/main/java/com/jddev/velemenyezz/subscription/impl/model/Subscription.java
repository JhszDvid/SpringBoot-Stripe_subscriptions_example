package com.jddev.velemenyezz.subscription.impl.model;

import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionStatus;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @Column(name = "stripe_event_id", unique = true)
    private String eventID;

    @Column(name = "stripe_subscription_id", length = 500)
    private String subscriptionID;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false, length = 50)
    private SubscriptionStatus subscriptionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type", length = 50)
    private SubscriptionType subscriptionType;



    public String getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    protected Subscription(){}


    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public static class Builder{
        Subscription subscription;
        public Builder(){
            subscription = new Subscription();
        }
        public Builder withOwnerEmail(String email){
            subscription.setOwnerEmail(email);
            return this;
        }
        public Builder withEventID(String eventID){
            subscription.setEventID(eventID);
            return this;
        }
        public Builder withSubscriptionStatus(SubscriptionStatus subscriptionStatus){
            subscription.setSubscriptionStatus(subscriptionStatus);
            return this;
        }
        public Builder withSubscriptionType(SubscriptionType subscriptionType){
            subscription.setSubscriptionType(subscriptionType);
            return this;
        }
        public Builder withPaidAt(LocalDateTime paidAt){
            subscription.setPaidAt(paidAt);
            return this;
        }

        public Builder withSubscriptionID(String id){
            subscription.setSubscriptionID(id);
            return this;
        }
        public Builder withExpiresAt(LocalDateTime expiresAt){
            subscription.setExpiresAt(expiresAt);
            return this;
        }
        public Subscription build(){
            return subscription;
        }
    }


}