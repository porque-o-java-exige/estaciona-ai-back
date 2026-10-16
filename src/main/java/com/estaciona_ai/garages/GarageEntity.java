package com.estaciona_ai.garages;

import com.estaciona_ai.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "garages")
public class GarageEntity {
    @Id
    @UuidGenerator
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "garage_features", joinColumns = @JoinColumn(name = "garage_id"))
    @Column(name = "feature", nullable = false, length = 50)
    private Set<String> features = new HashSet<>();
    @Column(nullable = false)
    private Boolean available;
    private BigDecimal pricePerDay;
    private BigDecimal pricePerHour;
    @Column(name = "cover_image_url")
    private String coverImageUrl; // Imagem exibida no pin do mapa e nos cards de busca

    @ElementCollection
    @CollectionTable(name = "garage_images", joinColumns = @JoinColumn(name = "garage_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>(); // Galeria de fotos da garagem
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}