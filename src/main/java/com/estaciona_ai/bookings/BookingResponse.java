package com.estaciona_ai.bookings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponse(
        UUID bookingId,
        UUID driverId,
        UUID garageId,
        UUID vehicleId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        BigDecimal totalAmount,
        BookingType bookingType,
        BookingStatus status,
        LocalDateTime createdAt
) {}