package com.jddev.velemenyezz.review.impl.dto;

public record PostReviewRequest(
        String email,
        String text,
        Short rating
) {
}
