package com.estaciona_ai.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID bookingId,
        PaymentMethodEnum paymentMethod,
        PaymentStatusEnum status,
        BigDecimal totalAmount,
        BigDecimal platformFee,
        BigDecimal ownerNetAmount,
        String mpPaymentId,
        String qrCode,
        String qrCodeBase64,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}