package com.estaciona_ai.reviews.driver;

import java.util.UUID;

public record DriverRatingSummary(
        UUID driverId,
        Double averageRating,
        Long totalReviews
) {}