package com.estaciona_ai.chat.room;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatRoomResponse(
        UUID roomId,
        UUID bookingId,
        UUID driverId,
        UUID ownerId,
        LocalDateTime createdAt
){
}
