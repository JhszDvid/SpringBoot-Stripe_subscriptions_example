package com.jddev.velemenyezz.business.impl.service;

import com.jddev.velemenyezz.business.impl.dto.CreateBusinessRequest;
import com.jddev.velemenyezz.business.impl.exception.exceptions.BusinessNotFoundException;
import com.jddev.velemenyezz.business.impl.model.Business;
import com.jddev.velemenyezz.business.impl.repository.BusinessRepository;
import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.shared.exception.InternalErrorException;
import com.jddev.velemenyezz.subscription.api.SubscriptionModuleApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  BusinessService {

    Logger logger = LoggerFactory.getLogger(BusinessService.class);
    private final SharedMethods sharedMethods;

    private final BusinessRepository businessRepository;

    public BusinessService(SharedMethods sharedMethods, BusinessRepository businessRepository) {
        this.sharedMethods = sharedMethods;
        this.businessRepository = businessRepository;
    }

    public void validateBusinessID(Long id){
        businessRepository.findById(id).orElseThrow(() -> new BusinessNotFoundException("Business with this ID could not be found!"));
    }

    public void validateBusinessOwner(Long businessID, String owner){
        businessRepository.findByIdAndOwnerEmail(businessID, owner).orElseThrow(() -> new BusinessNotFoundException("Business with this ID and owner could not be found!"));
    }

    public Business getBusinessData(Long id) {
        String ownerEmail = sharedMethods.getAuthenticatedUserEmail();
        logger.info("Fetching business data for business id: " + id + ", user email: " + ownerEmail);
        return businessRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new BusinessNotFoundException("Business with this ID could not be found!"));
    }

    public List<Business> getBusinessesByOwner() {
        String ownerEmail = sharedMethods.getAuthenticatedUserEmail();
        logger.info("Fetching businesses for owner: " + ownerEmail);
        return businessRepository.findByOwnerEmail(ownerEmail);
    }

    public Business createBusiness(CreateBusinessRequest request){
        String ownerEmail = sharedMethods.getAuthenticatedUserEmail();
        logger.info("Creating business for owner: " + ownerEmail);
        Business newBusiness = new Business.Builder()
                .withOwner(ownerEmail)
                .withName(request.name())
                .withGoogleReview(request.googleReview())
                .withWebsiteURL(request.websiteUrl())
                .withLocation(request.location())
                .build();


        try{
            logger.info("Saving business to database...");
            businessRepository.save(newBusiness);
        }
        catch (Exception e){
            throw new InternalErrorException("ERROR: " + e.getMessage());
        }

        logger.info("FINISHED");
        return newBusiness;
    }
}
