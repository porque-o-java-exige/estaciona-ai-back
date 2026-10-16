package com.estaciona_ai.reviews.garage;

import java.time.LocalDateTime;
import java.util.UUID;

public record GarageReviewResponse(
        UUID id,
        UUID bookingId,
        UUID garageId,
        String driverName,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}