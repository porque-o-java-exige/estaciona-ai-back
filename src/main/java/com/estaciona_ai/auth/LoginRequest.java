package com.estaciona_ai.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para autenticação do usuário")
public record LoginRequest(
        @Schema(description = "E-mail cadastrado do usuário", example = "motorista@email.com")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "123456789")
        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}