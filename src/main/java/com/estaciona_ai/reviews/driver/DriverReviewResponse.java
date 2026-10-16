package com.estaciona_ai.reviews.driver;

import java.time.LocalDateTime;
import java.util.UUID;

public record DriverReviewResponse(
        UUID id,
        UUID bookingId,
        UUID driverId,
        String ownerName,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}