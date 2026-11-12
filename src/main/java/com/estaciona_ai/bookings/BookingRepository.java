package com.estaciona_ai.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    List<BookingEntity> findByDriverId(UUID driverId);
    List<BookingEntity> findByGarageId(UUID garageId);
    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM BookingEntity b
    WHERE b.garage.id = :garageId
      AND b.status <> 'CANCELLED'
      AND b.startDateTime < :endDateTime
      AND b.endDateTime > :startDateTime
""")
    boolean existsOverlappingBooking(
            @Param("garageId") UUID garageId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}
