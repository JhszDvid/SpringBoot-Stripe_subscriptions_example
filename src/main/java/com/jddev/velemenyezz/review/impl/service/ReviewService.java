package com.jddev.velemenyezz.review.impl.service;

import com.jddev.velemenyezz.business.api.BusinessModuleApi;
import com.jddev.velemenyezz.review.impl.dto.PostReviewRequest;
import com.jddev.velemenyezz.review.impl.exception.exceptions.ReviewNotFoundException;
import com.jddev.velemenyezz.review.impl.model.Review;
import com.jddev.velemenyezz.review.impl.repository.ReviewRepository;
import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.shared.exception.InternalErrorException;
import com.jddev.velemenyezz.subscription.api.SubscriptionModuleApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final BusinessModuleApi businessModuleApi;

    private final SharedMethods sharedMethods;

    public ReviewService(ReviewRepository reviewRepository, BusinessModuleApi businessModuleApi, SharedMethods sharedMethods) {
        this.reviewRepository = reviewRepository;
        this.businessModuleApi = businessModuleApi;
        this.sharedMethods = sharedMethods;
    }

    public List<Review> getReviewsByBusinessID(Long id){

        String ownerEmail = sharedMethods.getAuthenticatedUserEmail();
        logger.info("validating business owner(" +ownerEmail+") with business ID("+id+")");
        businessModuleApi.validateBusinessOwner(id, ownerEmail);
        logger.info("retrieving reviews...");
        List<Review> reviews = reviewRepository.findByBusinessID(id);
        logger.info("returning reviews (size: "+reviews.size()+")");
        if(reviews.isEmpty())
            throw new ReviewNotFoundException("A Review for this business could not be found!");

        logger.info("getReviewsByBusinessID END");
        return reviews;
    }

    public Review postReviewForBusiness(Long businessID, PostReviewRequest request){
        logger.info("postReviewForBusiness START, businessID: " +businessID);
        businessModuleApi.validateBusinessID(businessID);

        Review newReview = new Review.Builder()
                .withBusinessID(businessID)
                .withEmail(request.email())
                .withRating(request.rating())
                .withText(request.text())
                .build();

        try{
            logger.info("saving review object...");
            reviewRepository.save(newReview);
        }
        catch (Exception e){
            throw new InternalErrorException("ERROR: " + e.getMessage());
        }

        return newReview;
    }
}
