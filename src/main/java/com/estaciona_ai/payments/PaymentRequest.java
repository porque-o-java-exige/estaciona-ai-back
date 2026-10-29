package com.estaciona_ai.payments;

import java.util.UUID;

public record PaymentRequest(
        UUID bookingId,
        PaymentMethodEnum paymentMethod,
        String payerEmail,
        String payerCpf,
        String cardToken
) {
}