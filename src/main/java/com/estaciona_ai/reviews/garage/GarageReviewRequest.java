package com.estaciona_ai.reviews.garage;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record GarageReviewRequest(
        @NotNull(message = "O ID da reserva é obrigatório")
        UUID bookingId,

        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer rating,

        @Size(max = 500, message = "O comentário pode ter no máximo 500 caracteres")
        String comment
) {}