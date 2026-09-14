package com.estaciona_ai.garages;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GarageResponse(
        UUID garageId,
        UUID ownerId,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String description,
        String photo,
        BigDecimal price,
        Boolean available,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        BigDecimal pricePerDay,
        BigDecimal pricePerHour
) {
}
