package com.estaciona_ai.chat.message;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageResponse(
        UUID messageId,
        UUID roomId,
        UUID senderId,
        String content,
        LocalDateTime createdAt
) {}