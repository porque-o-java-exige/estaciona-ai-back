package com.estaciona_ai.vehicles;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VehicleResponse {

    private UUID id;
    private UUID userId;
    private String licensePlate;
    private Integer year;
    private String model;
    private String color;
    private LocalDateTime createdAt;

    public VehicleResponse(VehicleEntity vehicle) {
        this.id = vehicle.getId();
        this.userId = vehicle.getUser().getId();
        this.licensePlate = vehicle.getLicensePlate();
        this.year = vehicle.getYear();
        this.model = vehicle.getModel();
        this.color = vehicle.getColor();
        this.createdAt = vehicle.getCreatedAt();
    }
}