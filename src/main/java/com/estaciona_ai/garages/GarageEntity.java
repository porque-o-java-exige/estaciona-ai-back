package com.estaciona_ai.garages;

import com.estaciona_ai.users.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "garages")
public class GarageEntity {
    @Id
    @UuidGenerator
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String photo;
    private BigDecimal price;
    @Column(nullable = false)
    private Boolean available;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private BigDecimal pricePerDay;
    private BigDecimal pricePerHour;
}