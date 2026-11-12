package com.estaciona_ai.reviews.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Dados para avaliação de um motorista pelo dono da garagem")
public record DriverReviewRequest(
        @Schema(description = "ID da reserva finalizada", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        @NotNull(message = "O ID da reserva é obrigatório")
        UUID bookingId,

        @Schema(description = "Nota atribuída ao motorista de 1 a 5", example = "5")
        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer rating,

        @Schema(description = "Comentário opcional sobre a conduta do motorista", example = "Motorista extremamente pontual e cuidadoso ao estacionar.")
        @Size(max = 500, message = "O comentário pode ter no máximo 500 caracteres")
        String comment
) {}