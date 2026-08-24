package com.estaciona_ai.users;

public record UserResponse(
        String name,
        String email,
        String phoneNumber
) {
}