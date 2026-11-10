package com.estaciona_ai.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para realização de reserva de vaga")
public record BookingRequest(
        @Schema(description = "ID da garagem desejada", example = "a1111111-1111-1111-1111-111111111111")
        @NotNull
        UUID garageId,

        @Schema(description = "ID do veículo que utilizará a vaga", example = "6f87d720-11e1-45b4-a20e-f667b0961404")
        @NotNull
        UUID vehicleId,

        @Schema(description = "Modalidade de precificação da reserva", example = "HOURLY")
        @NotNull
        BookingType bookingType,

        @Schema(description = "Data e horário inicial da reserva", example = "2026-11-01T10:00:00")
        @NotNull @FutureOrPresent
        LocalDateTime startDateTime,

        @Schema(description = "Data e horário final da reserva", example = "2026-11-01T14:00:00")
        @NotNull @Future
        LocalDateTime endDateTime
) {}