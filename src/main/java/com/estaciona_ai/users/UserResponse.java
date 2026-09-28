package com.estaciona_ai.users;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String phoneNumber
) {
}