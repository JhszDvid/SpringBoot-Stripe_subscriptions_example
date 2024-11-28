package com.jddev.velemenyezz.business.api;

import com.jddev.velemenyezz.business.impl.model.Business;
import com.jddev.velemenyezz.business.impl.service.BusinessService;
import org.springframework.stereotype.Service;

@Service
public class BusinessModuleApi {

    private final BusinessService businessService;

    public BusinessModuleApi(BusinessService businessService) {
        this.businessService = businessService;
    }

    public void validateBusinessID(Long id){
        businessService.validateBusinessID(id);
    }
    public void validateBusinessOwner(Long id, String owner){businessService.validateBusinessOwner(id, owner);}
}
