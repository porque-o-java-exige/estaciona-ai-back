package com.estaciona_ai.payments;

import com.estaciona_ai.bookings.BookingEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatusEnum status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // Comissão da plataforma, correspondente a 10% conforme o script
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal platformFee;

    // Valor líquido do proprietário, correspondente a 90%
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ownerNetAmount;

    // Identificador do pagamento no Mercado Pago
    @Column(length = 100)
    private String mpPaymentId;

    // Código Pix gerado
    @Column(columnDefinition = "TEXT")
    private String qrCode;

    // QR Code em formato Base64
    @Column(columnDefinition = "TEXT")
    private String qrCodeBase64;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}