package com.estaciona_ai.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do cadastro da conta do usuário")
public enum StatusEnum {
    @Schema(description = "Conta ativa e com acesso liberado")
    ACTIVE,

    @Schema(description = "Conta aguardando confirmação ou ativação")
    PENDING,

    @Schema(description = "Conta suspensa temporariamente")
    SUSPENDED,

    @Schema(description = "Conta banida do sistema por violação de regras")
    BANNED,

    @Schema(description = "Conta inativa/desativada pelo próprio usuário")
    INACTIVE
}