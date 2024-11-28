package com.jddev.velemenyezz.review.impl.repository;

import com.jddev.velemenyezz.review.impl.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBusinessID(Long businessID);



}
