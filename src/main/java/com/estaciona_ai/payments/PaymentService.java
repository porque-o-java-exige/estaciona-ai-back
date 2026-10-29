
package com.estaciona_ai.payments;

import com.estaciona_ai.bookings.BookingEntity;
import com.estaciona_ai.bookings.BookingRepository;
import com.estaciona_ai.bookings.BookingStatus;
import com.estaciona_ai.notifications.NotificationService;
import com.estaciona_ai.users.UserEntity;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;
    private final MercadoPagoService mercadoPagoService;
    private final NotificationService notificationService;

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {

        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Reserva não encontrada"));

        if (booking.getStatus() == null
                || booking.getStatus() == BookingStatus.CANCELLED
                || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Não é possível pagar uma reserva cancelada ou concluída");
        }

        if (request.paymentMethod() != PaymentMethodEnum.PIX) {
            throw new IllegalArgumentException(
                    "Nesta etapa, somente pagamentos Pix estão disponíveis");
        }

        if (request.payerEmail() == null
                || request.payerEmail().isBlank()
                || request.payerCpf() == null
                || request.payerCpf().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail e o CPF do pagador são obrigatórios");
        }

        BigDecimal totalAmount = booking.getTotalAmount();

        BigDecimal platformFee = totalAmount
                .multiply(new BigDecimal("0.10"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal ownerNetAmount = totalAmount.subtract(platformFee);

        // Cria a cobrança Pix no Mercado Pago.
        JsonNode mpResponse = mercadoPagoService.criarPix(
                totalAmount,
                request.payerEmail(),
                request.payerCpf(),
                booking.getId()
        );

        String mpPaymentId = mpResponse.path("id").asText();

        JsonNode transactionData = mpResponse
                .path("point_of_interaction")
                .path("transaction_data");

        String qrCode = transactionData.path("qr_code").asText(null);
        String qrCodeBase64 = transactionData
                .path("qr_code_base64").asText(null);

        if (mpPaymentId.isBlank()
                || transactionData.isMissingNode()
                || qrCode == null
                || qrCode.isBlank()) {
            throw new IllegalStateException(
                    "O Mercado Pago não retornou os dados esperados para o Pix");
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setBooking(booking);
        payment.setPaymentMethod(PaymentMethodEnum.PIX);
        payment.setStatus(PaymentStatusEnum.PENDING);
        payment.setTotalAmount(totalAmount);
        payment.setPlatformFee(platformFee);
        payment.setOwnerNetAmount(ownerNetAmount);
        payment.setMpPaymentId(mpPaymentId);
        payment.setQrCode(qrCode);
        payment.setQrCodeBase64(qrCodeBase64);

        PaymentEntity savedPayment = paymentRepository.save(payment);

        return paymentMapper.toResponse(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(UUID id) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pagamento não encontrado"));

        return paymentMapper.toResponse(payment);
    }

    /**
     * Envia as notificações de uma aprovação.
     * Deve ser chamado após a confirmação do pagamento.
     */
    @Transactional
    public void notifyPaymentApproved(PaymentEntity payment) {

        BookingEntity booking = payment.getBooking();

        UserEntity driver = booking.getDriver();
        UserEntity owner = booking.getGarage().getOwner();
        String garageName = booking.getGarage().getName();

        notificationService.notifyBookingConfirmed(
                driver,
                owner,
                garageName
        );
    }

    /**
     * Método exclusivo para simulação durante os testes.
     * Não confirma pagamentos reais no Mercado Pago.
     */
    @Transactional
    public PaymentResponse simulateApproval(UUID id) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pagamento não encontrado"));

        if (payment.getStatus() != PaymentStatusEnum.PENDING) {
            throw new IllegalStateException(
                    "Somente pagamentos pendentes podem ser aprovados");
        }

        payment.setStatus(PaymentStatusEnum.APPROVED);

        BookingEntity booking = payment.getBooking();

        // A reserva foi confirmada, mas ainda não foi concluída.
        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        PaymentEntity savedPayment = paymentRepository.save(payment);

        // Notificação simulada: não representa uma confirmação do Mercado Pago.
        notifyPaymentApproved(savedPayment);

        return paymentMapper.toResponse(savedPayment);
    }
}