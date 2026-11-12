package com.estaciona_ai.payments;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para criação da solicitação de pagamento")
public record PaymentRequest(
        @Schema(description = "ID da reserva a ser paga", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "Método de pagamento escolhido", example = "PIX")
        PaymentMethodEnum paymentMethod,

        @Schema(description = "E-mail do pagador", example = "motorista@email.com")
        String payerEmail,

        @Schema(description = "CPF válido do pagador (apenas números)", example = "19102830005")
        String payerCpf,

        @Schema(description = "Token do cartão de crédito (obrigatório se paymentMethod for CREDIT_CARD)", example = "card_token_xyz")
        String cardToken
) {
}