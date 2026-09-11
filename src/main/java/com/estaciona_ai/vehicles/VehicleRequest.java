package com.estaciona_ai.vehicles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class VehicleRequest {

    @NotBlank
    private String licensePlate;

    private Integer year;

    private String model;

    private String color;
}