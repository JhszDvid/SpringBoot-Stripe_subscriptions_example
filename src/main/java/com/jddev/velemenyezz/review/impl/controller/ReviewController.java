package com.jddev.velemenyezz.review.impl.controller;

import com.jddev.velemenyezz.review.impl.dto.PostReviewRequest;
import com.jddev.velemenyezz.review.impl.model.Review;
import com.jddev.velemenyezz.review.impl.service.ReviewService;
import com.jddev.velemenyezz.shared.annotations.SubscriptionRequired;
import com.jddev.velemenyezz.shared.response.APIResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    @SubscriptionRequired
    public ResponseEntity<?> getReviewsByBusinessID(@PathVariable Long id){
        // for now return a simple list, pageable later.
        List<Review> reviewList = reviewService.getReviewsByBusinessID(id);

        return new APIResponseObject.Builder()
                .withObject(reviewList)
                .buildResponse();
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<?> postReview(@PathVariable Long id, @RequestBody PostReviewRequest request){
        Review postedReview = reviewService.postReviewForBusiness(id, request);
        return new APIResponseObject.Builder()
                .withMessage("Review successfully posted!")
                .withObject(postedReview)
                .buildResponse();
    }
}
