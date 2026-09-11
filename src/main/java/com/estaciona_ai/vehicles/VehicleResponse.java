package com.estaciona_ai.vehicles;


import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        UUID userId,
        String licensePlate,
        Integer year,
        String model,
        String color,
        LocalDateTime createdAt
) {
}