package com.estaciona_ai.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualização de usuário")
public record UserRequest(
        @Schema(description = "Nome completo do usuário", example = "Novo Motorista")
        @NotBlank
        String name,

        @Schema(description = "Endereço de e-mail único", example = "motorista@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "Senha de acesso (mínimo de 6 caracteres)", example = "123456789")
        @NotBlank
        @Size(min = 6, max = 18)
        String password,

        @Schema(description = "Número de telefone ou celular com DDD", example = "11999998888")
        @NotBlank
        @Size(max = 18)
        String phoneNumber
) {
}