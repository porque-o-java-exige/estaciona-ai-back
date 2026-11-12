package com.estaciona_ai.notifications;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados da notificação do sistema enviada ao usuário")
public record NotificationResponse(
        @Schema(description = "ID único da notificação", example = "3b07e730-23e1-47b4-a20e-f667b0961203")
        UUID id,

        @Schema(description = "Título da notificação", example = "Reserva Confirmada")
        String title,

        @Schema(description = "Mensagem ou conteúdo da notificação", example = "Sua reserva para a Garagem MASP foi confirmada com sucesso!")
        String message,

        @Schema(description = "Indica se a notificação já foi lida pelo usuário", example = "false")
        boolean isRead,

        @Schema(description = "Data e hora do envio da notificação")
        LocalDateTime createdAt
) {
}