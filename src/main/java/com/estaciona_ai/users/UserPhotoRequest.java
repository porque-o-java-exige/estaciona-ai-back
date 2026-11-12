package com.estaciona_ai.users;

import jakarta.validation.constraints.NotBlank;

public record UserPhotoRequest(
        @NotBlank(message = "A URL da foto não pode estar vazia")
        String photoUrl
) {
}