package com.estaciona_ai.users;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados do perfil de usuário retornado pela API")
public record UserResponse(
        @Schema(description = "Identificador único do usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID id,

        @Schema(description = "Nome completo do usuário", example = "Novo Motorista")
        String name,

        @Schema(description = "Endereço de e-mail", example = "motorista@email.com")
        String email,

        @Schema(description = "Número de telefone", example = "11999998888")
        String phoneNumber,

        @Schema(description = "URL pública da foto de perfil hospedada no Azure Storage", example = "https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/user.png")
        String photoUrl
) {
}