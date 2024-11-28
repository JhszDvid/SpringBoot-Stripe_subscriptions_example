package com.jddev.velemenyezz.subscription.impl.exception.exceptions;

public class StripeWebhookProcessingException extends RuntimeException{
    public StripeWebhookProcessingException(String message) {
        super(message);
    }
}
