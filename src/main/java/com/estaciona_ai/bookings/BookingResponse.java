package com.estaciona_ai.bookings;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados detalhados da reserva criada ou consultada")
public record BookingResponse(
        @Schema(description = "ID único da reserva", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "ID do motorista", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID driverId,

        @Schema(description = "ID da garagem", example = "a1111111-1111-1111-1111-111111111111")
        UUID garageId,

        @Schema(description = "ID do veículo", example = "6f87d720-11e1-45b4-a20e-f667b0961404")
        UUID vehicleId,

        @Schema(description = "Início do período da reserva", example = "2026-11-01T10:00:00")
        LocalDateTime startDateTime,

        @Schema(description = "Fim do período da reserva", example = "2026-11-01T14:00:00")
        LocalDateTime endDateTime,

        @Schema(description = "Valor total calculado da reserva (R$)", example = "48.00")
        BigDecimal totalAmount,

        @Schema(description = "Tipo de cobrança (HOURLY ou DAILY)", example = "HOURLY")
        BookingType bookingType,

        @Schema(description = "Status da reserva (PENDING, CONFIRMED, CANCELLED, COMPLETED)", example = "PENDING")
        BookingStatus status,

        @Schema(description = "Data e hora em que a reserva foi criada")
        LocalDateTime createdAt
) {}