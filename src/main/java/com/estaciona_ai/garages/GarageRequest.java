package com.estaciona_ai.garages;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

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
        @Size(max = 500)
        String photo,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal price,
        @NotNull
        Boolean available,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal pricePerDay,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal pricePerHour

) {
}
