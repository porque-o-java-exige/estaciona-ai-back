package com.estaciona_ai.reviews.garage;

import java.util.UUID;

public record GarageRatingSummary(
        UUID garageId,
        Double averageRating,
        Long totalReviews
) {}