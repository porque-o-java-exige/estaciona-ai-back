package com.estaciona_ai.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    List<BookingEntity> findByDriverId(UUID driverId);
    List<BookingEntity> findByGarageId(UUID garageId);
}
