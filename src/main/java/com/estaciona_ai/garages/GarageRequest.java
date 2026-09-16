package com.estaciona_ai.garages;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record GarageRequest(
        @NotBlank
        @Size(max = 150)
        String name,
        @NotBlank
        @Size(max = 255)
        String address,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude,
        @Size(max = 1000)
        String description,
        Set<@NotBlank @Size(max = 50) String> features,
        @NotNull
        Boolean available,
        @DecimalMin("0.0")
        BigDecimal pricePerDay,
        @DecimalMin("0.0")
        BigDecimal pricePerHour
) {
}
