package com.jddev.velemenyezz.review;

import com.jddev.velemenyezz.business.api.BusinessModuleApi;
import com.jddev.velemenyezz.review.impl.repository.ReviewRepository;
import com.jddev.velemenyezz.review.impl.service.ReviewService;
import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.subscription.api.SubscriptionModuleApi;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionStatus;
import com.jddev.velemenyezz.subscription.impl.enums.SubscriptionType;
import com.jddev.velemenyezz.subscription.impl.model.Subscription;
import com.jddev.velemenyezz.subscription.impl.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ReviewModuleTests {
    @InjectMocks
    private ReviewService reviewService;

    /* private final ReviewRepository reviewRepository;
    private final BusinessModuleApi businessModuleApi;

    private final SharedMethods sharedMethods;
    private final SubscriptionModuleApi subscriptionModuleApi;*/
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BusinessModuleApi businessModuleApi;
    @Mock
    private SharedMethods sharedMethods;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private SubscriptionModuleApi subscriptionModuleApi;

    /*  public List<Review> getReviewsByBusinessID(Long id){
        String ownerEmail = sharedMethods.getAuthenticatedUserEmail();
        subscriptionModuleApi.validateActiveSubscription(ownerEmail);
        businessModuleApi.validateBusinessOwner(id, ownerEmail); // errors out if the authed user does not own the business -> is this the best way? idk

        List<Review> reviews = reviewRepository.findByBusinessID(id);
        if(reviews.isEmpty())
            throw new ReviewNotFoundException("A Review for this business could not be found!");

        return reviews;
    }*/

    @Test
    void getReviewsByBusinessID_testWithSubscribedAuthedCorrectData() {
        String expectedEmail = "dev@revdev.com";
        Long businessID = 1L;
        String mockSubID = "test";

        Mockito.when(sharedMethods.getAuthenticatedUserEmail()).thenReturn(expectedEmail);
        Mockito.when(subscriptionRepository.findById(mockSubID)).thenReturn(
                Optional.ofNullable(new Subscription.Builder()
                        .withEventID(mockSubID)
                        .withOwnerEmail(expectedEmail)
                        .withSubscriptionType(SubscriptionType.STANDARD)
                        .withExpiresAt(LocalDateTime.now().plusMonths(1))
                        .withPaidAt(LocalDateTime.now())
                        .withSubscriptionStatus(SubscriptionStatus.ACTIVE)
                        .build())
        );

    }


}
