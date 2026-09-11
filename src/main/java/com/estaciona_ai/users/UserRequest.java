package com.estaciona_ai.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 6, max = 18)
         String password,
        @NotBlank
        @Size(max = 18)
         String phoneNumber
) {
}
