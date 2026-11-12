package com.estaciona_ai.garages;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Dados completos da vaga de garagem")
public record GarageResponse(
        @Schema(description = "ID único da garagem", example = "a1111111-1111-1111-1111-111111111111")
        UUID garageId,

        @Schema(description = "ID do proprietário", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID ownerId,

        @Schema(description = "Nome da garagem", example = "Garagem MASP Consolação")
        String name,

        @Schema(description = "Endereço completo", example = "Av. Paulista, 1578 - Bela Vista, São Paulo - SP")
        String address,

        @Schema(description = "Latitude", example = "-23.561414")
        Double latitude,

        @Schema(description = "Longitude", example = "-46.655881")
        Double longitude,

        @Schema(description = "Descrição da vaga", example = "Vaga coberta e com vigilância 24h.")
        String description,

        @Schema(description = "Comodidades da garagem")
        Set<String> features,

        @Schema(description = "Preço base de exibição (R$)", example = "12.00")
        BigDecimal price,

        @Schema(description = "Status de disponibilidade da garagem", example = "true")
        Boolean available,

        @Schema(description = "Data e hora do cadastro")
        LocalDateTime createdAt,

        @Schema(description = "Data e hora da última atualização")
        LocalDateTime updatedAt,

        @Schema(description = "Valor diário (R$)", example = "80.00")
        BigDecimal pricePerDay,

        @Schema(description = "Valor por hora (R$)", example = "12.00")
        BigDecimal pricePerHour,

        @Schema(description = "URL da imagem de capa para ser exibida nos cards", example = "https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/garages/cover.jpg")
        String coverImageUrl,

        @Schema(description = "Galeria com URLs das imagens secundárias da garagem")
        List<String> images
) {
}