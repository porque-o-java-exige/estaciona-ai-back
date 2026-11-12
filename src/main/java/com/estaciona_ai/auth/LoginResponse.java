package com.estaciona_ai.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta do login contendo os dados do usuário e o Token JWT")
public record LoginResponse(
        @Schema(description = "Token de acesso no padrão JWT", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Tipo de token para uso no header Authorization", example = "Bearer")
        String tokenType,

        @Schema(description = "Identificador único do usuário autenticado", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID userId,

        @Schema(description = "Nome completo do usuário", example = "Novo Motorista")
        String name,

        @Schema(description = "E-mail do usuário", example = "motorista@email.com")
        String email
) {
}