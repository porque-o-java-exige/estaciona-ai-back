package com.estaciona_ai.security;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email,
        String name
) {
}