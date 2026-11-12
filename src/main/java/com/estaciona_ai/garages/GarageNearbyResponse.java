package com.estaciona_ai.garages;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Resumo da garagem localizada nas proximidades com distância calculada em Km")
public record GarageNearbyResponse(
        @Schema(description = "ID único da garagem", example = "a1111111-1111-1111-1111-111111111111")
        UUID id,

        @Schema(description = "Nome do local", example = "Garagem MASP Consolação")
        String name,

        @Schema(description = "Endereço completo", example = "Av. Paulista, 1578 - Bela Vista, São Paulo - SP")
        String address,

        @Schema(description = "Latitude da vaga", example = "-23.561414")
        Double latitude,

        @Schema(description = "Longitude da vaga", example = "-46.655881")
        Double longitude,

        @Schema(description = "Preço por hora (R$)", example = "12.00")
        BigDecimal pricePerHour,

        @Schema(description = "Preço por dia (R$)", example = "80.00")
        BigDecimal pricePerDay,

        @Schema(description = "Disponibilidade atual", example = "true")
        Boolean available,

        @Schema(description = "Distância estimada em quilômetros em relação ao ponto buscado", example = "1.25")
        Double distanceInKm
) {}