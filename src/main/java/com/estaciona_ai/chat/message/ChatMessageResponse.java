package com.estaciona_ai.chat.message;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados da mensagem enviada ou recuperada do histórico")
public record ChatMessageResponse(
        @Schema(description = "ID da mensagem", example = "7a07e730-23e1-47b4-a20e-f667b0961707")
        UUID messageId,

        @Schema(description = "ID da sala de chat", example = "5e07e730-23e1-47b4-a20e-f667b0961805")
        UUID roomId,

        @Schema(description = "ID do usuário remetente", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID senderId,

        @Schema(description = "Conteúdo da mensagem", example = "Olá, estou a caminho da garagem!")
        String content,

        @Schema(description = "Data e hora do envio")
        LocalDateTime createdAt
) {}