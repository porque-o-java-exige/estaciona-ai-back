package com.estaciona_ai.vehicles;


import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponse(
        UUID id,
        UUID ownerId,
        String licensePlate,
        Integer year,
        String brand,
        String model,
        String color
) {
}