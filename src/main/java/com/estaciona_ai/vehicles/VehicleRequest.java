package com.estaciona_ai.vehicles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Dados para cadastro ou edição de um veículo")
public record VehicleRequest(
        @Schema(description = "Placa do veículo no padrão Mercosul ou antigo", example = "ABC1D23")
        @NotBlank(message = "A placa é obrigatória")
        @Size(min = 7, max = 8)
        @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Formato de placa inválido")
        String licensePlate,

        @Schema(description = "Ano de fabricação", example = "2023")
        @NotNull(message = "O ano é obrigatório")
        @Min(value = 1900, message = "Ano inválido")
        Integer year,

        @Schema(description = "Marca do veículo", example = "TOYOTA")
        @NotBlank(message = "A marca é obrigatória")
        @Size(max = 50, message = "A marca deve ter no máximo 50 caracteres")
        String brand,

        @Schema(description = "Modelo do veículo", example = "COROLLA")
        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
        String model,

        @Schema(description = "Cor do veículo", example = "BLACK")
        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 50, message = "A cor deve ter no máximo 50 caracteres")
        String color
) {
}