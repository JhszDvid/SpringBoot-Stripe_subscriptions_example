package com.jddev.velemenyezz.shared;

import com.jddev.velemenyezz.shared.exception.AuthenticationException;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SharedMethods {

    public String getAuthenticatedUserEmail()
    {
        try{
            Jwt authToken = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return authToken.getClaimAsString("email");
        }
        catch (Exception e){
            throw new AuthenticationException();
        }
    }


}
