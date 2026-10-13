package com.estaciona_ai.garages;

import java.math.BigDecimal;
import java.util.UUID;

public record GarageNearbyResponse(
        UUID id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        BigDecimal pricePerHour,
        BigDecimal pricePerDay,
        Boolean available,
        Double distanceInKm
) {}