package com.estaciona_ai.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByMpPaymentId(String mpPaymentId);

    Optional<PaymentEntity> findByBooking_Id(UUID bookingId);
}