package com.jddev.velemenyezz.business.impl.exception;

import com.jddev.velemenyezz.business.impl.exception.exceptions.BusinessNotFoundException;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import com.jddev.velemenyezz.shared.response.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value={BusinessNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(BusinessNotFoundException ex)
    {
        return new APIResponseObject.Builder()
                .withMessage(ex.getMessage())
                .withStatusCode(HttpStatus.NOT_FOUND)
                .withResponseType(ResponseType.ERROR)
                .buildResponse();
    }
}
