package com.estaciona_ai.reviews.garage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GarageReviewRepository extends JpaRepository<GarageReviewEntity, UUID> {

    boolean existsByBookingId(UUID bookingId);

    Page<GarageReviewEntity> findByGarageIdOrderByCreatedAtDesc(UUID garageId, Pageable pageable);

    @Query("SELECT AVG(gr.rating) FROM GarageReviewEntity gr WHERE gr.garage.id = :garageId")
    Optional<Double> findAverageRatingByGarageId(@Param("garageId") UUID garageId);

    Long countByGarageId(UUID garageId);
}