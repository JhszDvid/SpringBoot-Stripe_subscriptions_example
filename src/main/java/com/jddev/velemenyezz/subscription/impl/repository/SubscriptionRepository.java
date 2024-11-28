package com.jddev.velemenyezz.subscription.impl.repository;

import com.jddev.velemenyezz.subscription.impl.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    Optional<Subscription> findBySubscriptionID(String subscriptionID);


}
