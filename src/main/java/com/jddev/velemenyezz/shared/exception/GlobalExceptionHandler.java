package com.jddev.velemenyezz.shared.exception;

import com.jddev.velemenyezz.review.impl.exception.exceptions.ReviewNotFoundException;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.AlreadySubscribedException;
import com.jddev.velemenyezz.subscription.impl.exception.exceptions.InactiveSubscriptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler{

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value={AuthenticationException.class, InvalidBearerTokenException.class, JwtValidationException.class, AccessDeniedException.class})
    public ResponseEntity<Object> handleAuthenticationException(RuntimeException ex)
    {
        return new APIResponseObject.Builder()
                .withStatusCode(HttpStatus.UNAUTHORIZED)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }

    @ExceptionHandler(value={InternalErrorException.class})
    public ResponseEntity<Object> handleInternalError(InternalErrorException ex)
    {
        logger.error("INTERNAL_ERROR: " + ex.getMessage());
        return new APIResponseObject.Builder()
                .withMessage("Unexpected error!")
                .withStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }

}
