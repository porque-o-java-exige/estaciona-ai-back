package com.estaciona_ai.chat.message;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Estrutura da mensagem a ser enviada no chat")
public record ChatMessageRequest(
        @Schema(description = "ID da sala de conversa", example = "5e07e730-23e1-47b4-a20e-f667b0961805")
        UUID roomId,

        @Schema(description = "ID do usuário remetente", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID senderId,

        @Schema(description = "Conteúdo da mensagem enviada", example = "Olá, estou a caminho da garagem!")
        String content
) {}