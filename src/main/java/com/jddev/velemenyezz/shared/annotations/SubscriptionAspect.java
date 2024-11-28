package com.jddev.velemenyezz.shared.annotations;

import com.jddev.velemenyezz.shared.exception.AuthenticationException;
import com.jddev.velemenyezz.shared.events.ValidateSubscriptionEvent;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class SubscriptionAspect {
    @Autowired

    private ApplicationEventPublisher publisher;


    @Before("@annotation(com.jddev.velemenyezz.shared.annotations.SubscriptionRequired)")
    public void publishSubscriptionValidationEvent()
    {
        String ownerEmail;
        try{
            Jwt authToken = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ownerEmail =  authToken.getClaimAsString("email");
        }
        catch (Exception e){
            throw new AuthenticationException();
        }

        publisher.publishEvent(new ValidateSubscriptionEvent(ownerEmail));
    }
}
