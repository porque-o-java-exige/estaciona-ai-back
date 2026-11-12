package com.estaciona_ai.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Perfis/Papéis de acesso de usuários no sistema")
public enum RolesEnum {
    @Schema(description = "Usuário padrão (motorista ou proprietário de garagem)")
    ROLE_USER,

    @Schema(description = "Administrador do sistema")
    ROLE_ADMIN
}