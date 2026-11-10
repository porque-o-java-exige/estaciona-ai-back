package com.estaciona_ai.payments;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados do pagamento gerado com detalhes Pix/Mercado Pago")
public record PaymentResponse(
        @Schema(description = "ID único do pagamento no sistema", example = "1c07e730-23e1-47b4-a20e-f667b0961999")
        UUID id,

        @Schema(description = "ID da reserva vinculada", example = "9b07e730-23e1-47b4-a20e-f667b0961309")
        UUID bookingId,

        @Schema(description = "Método de pagamento", example = "PIX")
        PaymentMethodEnum paymentMethod,

        @Schema(description = "Status do pagamento (PENDING, APPROVED, REJECTED)", example = "PENDING")
        PaymentStatusEnum status,

        @Schema(description = "Valor total pago (R$)", example = "48.00")
        BigDecimal totalAmount,

        @Schema(description = "Taxa cobrada pela plataforma (R$)", example = "4.80")
        BigDecimal platformFee,

        @Schema(description = "Valor líquido repassado ao dono da garagem (R$)", example = "43.20")
        BigDecimal ownerNetAmount,

        @Schema(description = "ID da transação retornado pela API do Mercado Pago", example = "1234567890")
        String mpPaymentId,

        @Schema(description = "Código Copia e Cola do Pix", example = "00020126580014BR.GOV.BCB.PIX...")
        String qrCode,

        @Schema(description = "Imagem do QR Code em Base64 para exibição na tela", example = "iVBORw0KGgoAAAANSUhEUgAA...")
        String qrCodeBase64,

        @Schema(description = "Data de criação da cobrança")
        LocalDateTime createdAt,

        @Schema(description = "Data de atualização da cobrança")
        LocalDateTime updatedAt
) {
}