package com.estaciona_ai.vehicles;

import com.estaciona_ai.users.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    @UuidGenerator
    @Column(length = 36)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "license_plate", length = 20, nullable = false, unique = true)
    private String licensePlate;
    private int year;

    @Column(length = 100)
    private String model;

    @Column(length = 50)
    private String color;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}