package com.estaciona_ai.reviews.driver;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados da avaliação recebida por um motorista")
public record DriverReviewResponse(
        @Schema(description = "ID único da avaliação", example = "4a07e730-23e1-47b4-a20e-f667b0961404")
        UUID id,

        @Schema(description = "ID da reserva vinculada", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "ID do motorista avaliado", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID driverId,

        @Schema(description = "Nome do proprietário da garagem que realizou a avaliação", example = "Carlos Silva")
        String ownerName,

        @Schema(description = "Nota de 1 a 5", example = "5")
        Integer rating,

        @Schema(description = "Comentário registrado", example = "Motorista pontual.")
        String comment,

        @Schema(description = "Data e hora em que a avaliação foi realizada")
        LocalDateTime createdAt
) {}