package com.jddev.velemenyezz.business.impl.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateBusinessRequest(
        @Size(max = 255)
        @NotBlank
        String name,
        @Size(max = 255)
        @NotBlank
        String location,
        @Size(max=255)
        String websiteUrl,
        @Size(max=500)
        String googleReview

) {
}
