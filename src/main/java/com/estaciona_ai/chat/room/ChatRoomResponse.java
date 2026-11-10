package com.estaciona_ai.chat.room;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Detalhes da sala de chat entre o motorista e o dono da garagem")
public record ChatRoomResponse(
        @Schema(description = "ID único da sala de chat", example = "5e07e730-23e1-47b4-a20e-f667b0961805")
        UUID roomId,

        @Schema(description = "ID da reserva associada", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "ID do motorista", example = "8f07e730-23e1-47b4-a20e-f667b0961303")
        UUID driverId,

        @Schema(description = "ID do proprietário da garagem", example = "8ad50250-bf0b-4daf-b56f-d0db1703c991")
        UUID ownerId,

        @Schema(description = "Data e hora da criação da sala")
        LocalDateTime createdAt
){
}