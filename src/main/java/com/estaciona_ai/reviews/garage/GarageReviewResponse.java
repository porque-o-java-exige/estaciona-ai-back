package com.estaciona_ai.reviews.garage;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados da avaliação registrada sobre uma garagem")
public record GarageReviewResponse(
        @Schema(description = "ID único da avaliação", example = "2b07e730-23e1-47b4-a20e-f667b0961102")
        UUID id,

        @Schema(description = "ID da reserva vinculada", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "ID da garagem avaliada", example = "a1111111-1111-1111-1111-111111111111")
        UUID garageId,

        @Schema(description = "Nome do motorista que fez a avaliação", example = "Novo Motorista")
        String driverName,

        @Schema(description = "Nota de 1 a 5", example = "5")
        Integer rating,

        @Schema(description = "Comentário deixado pelo motorista", example = "Ótima garagem!")
        String comment,

        @Schema(description = "Data e hora em que a avaliação foi enviada")
        LocalDateTime createdAt
) {}