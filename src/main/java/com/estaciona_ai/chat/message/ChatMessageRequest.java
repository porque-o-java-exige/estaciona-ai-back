package com.estaciona_ai.chat.message;

import java.util.UUID;

public record ChatMessageRequest(
        UUID roomId,
        UUID senderId,
        String content
) {}