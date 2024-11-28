package com.jddev.velemenyezz.subscription.impl.exception;

import com.jddev.velemenyezz.shared.exception.GlobalExceptionHandler;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.AlreadySubscribedException;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.InactiveSubscriptionException;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.StripeWebhookProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SubscriptionExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value={InactiveSubscriptionException.class, AlreadySubscribedException.class})
    public ResponseEntity<Object> handleInactiveSubscription(RuntimeException ex)
    {
        return new APIResponseObject.Builder()
                .withMessage(ex.getMessage())
                .withStatusCode(HttpStatus.CONFLICT)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }

    @ExceptionHandler(value={StripeWebhookProcessingException.class})
    public ResponseEntity<Object> handleStripeException(RuntimeException ex)
    {
        logger.error(ex.getMessage());
        return new APIResponseObject.Builder()
                .withMessage("Failed to handle Stripe Webhook")
                .withStatusCode(HttpStatus.FAILED_DEPENDENCY)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }

}
