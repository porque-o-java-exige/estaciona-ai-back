package com.estaciona_ai.reviews.garage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Dados para avaliação de uma garagem pelo motorista")
public record GarageReviewRequest(
        @Schema(description = "ID da reserva realizada na garagem", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        @NotNull(message = "O ID da reserva é obrigatório")
        UUID bookingId,

        @Schema(description = "Nota dada à garagem de 1 a 5", example = "5")
        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer rating,

        @Schema(description = "Comentário sobre a vaga, segurança e facilidade de acesso", example = "Excelente vaga, muito limpa, segura e fácil de manobrar.")
        @Size(max = 500, message = "O comentário pode ter no máximo 500 caracteres")
        String comment
) {}