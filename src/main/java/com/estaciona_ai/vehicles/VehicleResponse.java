package com.estaciona_ai.vehicles;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados do veículo cadastrado")
public record VehicleResponse(
        @Schema(description = "ID único do veículo", example = "6f87d720-11e1-45b4-a20e-f667b0961404")
        UUID id,

        @Schema(description = "ID do proprietário do veículo", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID ownerId,

        @Schema(description = "Placa do veículo", example = "ABC1D23")
        String licensePlate,

        @Schema(description = "Ano de fabricação", example = "2023")
        Integer year,

        @Schema(description = "Marca do veículo", example = "TOYOTA")
        String brand,

        @Schema(description = "Modelo do veículo", example = "COROLLA")
        String model,

        @Schema(description = "Cor do veículo", example = "BLACK")
        String color
) {
}