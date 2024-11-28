package com.jddev.velemenyezz.business.impl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.business.impl.dto.CreateBusinessRequest;
import com.jddev.velemenyezz.business.impl.model.Business;
import com.jddev.velemenyezz.business.impl.service.BusinessService;
import com.jddev.velemenyezz.shared.annotations.SubscriptionRequired;
import com.jddev.velemenyezz.shared.exception.AuthenticationException;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import jakarta.validation.Valid;
import org.apache.tomcat.util.json.JSONParser;
import org.jboss.resteasy.spi.NotImplementedYetException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    @SubscriptionRequired
    public ResponseEntity<?> getBusinesses(){
        List<Business> businessList = businessService.getBusinessesByOwner();

        return new APIResponseObject.Builder()
                .withObject(businessList)
                .buildResponse();
    }

    @GetMapping("/{id}")
    @SubscriptionRequired
    public ResponseEntity<?> getBusinessData(@PathVariable Long id) {
        Business business = businessService.getBusinessData(id);

        return new APIResponseObject.Builder()
                .withObject(business)
                .buildResponse();
    }

    @PostMapping
    @SubscriptionRequired
    public ResponseEntity<?> createBusiness(@RequestBody @Valid CreateBusinessRequest request)
    {
         Business business = businessService.createBusiness(request);

         return new APIResponseObject.Builder()
                 .withMessage("Successfully created business!")
                 .withObject(business)
                 .buildResponse();
    }
}
