package com.estaciona_ai.vehicles;

import jakarta.validation.constraints.*;

public record VehicleRequest(

        @NotBlank(message = "A placa é obrigatória")
        @Size(min =7, max = 8)
        @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Formato de placa inválido")
        String licensePlate,
        @NotNull(message = "O ano é obrigatório")
        @Min(value = 1900, message = "Ano inválido")
        Integer year,
        @NotBlank(message = "A marca é obrigatória")
        @Size(max = 50, message = "A marca deve ter no máximo 50 caracteres")
        String brand,
        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
        String model,
        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 50, message = "A cor deve ter no máximo 50 caracteres")
        String color
) {

}