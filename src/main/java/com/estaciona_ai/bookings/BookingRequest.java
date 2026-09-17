package com.estaciona_ai.bookings;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingRequest(
        @NotNull
        UUID garageId,
        @NotNull
        UUID vehicleId,
        @NotNull
        BookingType bookingType,
        @NotNull @FutureOrPresent
        LocalDateTime startDateTime,
        @NotNull @Future
        LocalDateTime endDateTime
) {}