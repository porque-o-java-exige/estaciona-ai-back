package com.estaciona_ai.auth;

import java.util.UUID;

public record LoginResponse(
        String token,
        String tokenType,
        UUID userId,
        String name,
        String email
) {
}