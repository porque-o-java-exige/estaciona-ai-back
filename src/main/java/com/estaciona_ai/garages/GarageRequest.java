package com.estaciona_ai.garages;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Schema(description = "Dados para cadastro ou alteração de uma garagem")
public record GarageRequest(
        @Schema(description = "Nome da garagem", example = "Garagem MASP Consolação")
        @NotBlank
        @Size(max = 150)
        String name,

        @Schema(description = "Endereço completo", example = "Av. Paulista, 1578 - Bela Vista, São Paulo - SP")
        @NotBlank
        @Size(max = 255)
        String address,

        @Schema(description = "Latitude para posicionamento no mapa", example = "-23.561414")
        @NotNull
        Double latitude,

        @Schema(description = "Longitude para posicionamento no mapa", example = "-46.655881")
        @NotNull
        Double longitude,

        @Schema(description = "Descrição detalhada sobre acessos e regras", example = "Vaga coberta, portão eletrônico e câmeras de segurança 24h.")
        @Size(max = 1000)
        String description,

        @Schema(description = "Conjunto de comodidades disponíveis", example = "[\"COVERED\", \"SECURITY_CAMERA\", \"GATE_REMOTE\"]")
        Set<@NotBlank @Size(max = 50) String> features,

        @Schema(description = "Indica se a garagem está ativa para reservas", example = "true")
        @NotNull
        Boolean available,

        @Schema(description = "Valor cobrado pela diária inteira (R$)", example = "80.00")
        @DecimalMin("0.0")
        BigDecimal pricePerDay,

        @Schema(description = "Valor cobrado por hora de uso (R$)", example = "12.00")
        @DecimalMin("0.0")
        BigDecimal pricePerHour,

        @Schema(description = "URL da imagem principal de capa da garagem", example = "https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/garages/cover.jpg")
        @Size(max = 1000)
        String coverImageUrl,

        @Schema(description = "Lista com URLs das imagens da garagem")
        List<@NotBlank @Size(max = 1000) String> images
) {
}