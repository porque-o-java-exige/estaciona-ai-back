package com.estaciona_ai.reviews.driver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DriverReviewRepository extends JpaRepository<DriverReviewEntity, UUID> {

    boolean existsByBookingId(UUID bookingId);

    Page<DriverReviewEntity> findByDriverIdOrderByCreatedAtDesc(UUID driverId, Pageable pageable);

    @Query("SELECT AVG(dr.rating) FROM DriverReviewEntity dr WHERE dr.driver.id = :driverId")
    Optional<Double> findAverageRatingByDriverId(@Param("driverId") UUID driverId);

    Long countByDriverId(UUID driverId);
}