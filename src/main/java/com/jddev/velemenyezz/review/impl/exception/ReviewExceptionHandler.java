package com.jddev.velemenyezz.review.impl.exception;

import com.jddev.velemenyezz.business.impl.exception.exceptions.BusinessNotFoundException;
import com.jddev.velemenyezz.review.impl.exception.exceptions.ReviewNotFoundException;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReviewExceptionHandler {
    @ExceptionHandler(value={ReviewNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex)
    {
        return new APIResponseObject.Builder()
                .withMessage(ex.getMessage())
                .withStatusCode(HttpStatus.NOT_FOUND)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }
}
