package com.estaciona_ai.reviews.driver;

import com.estaciona_ai.bookings.BookingEntity;
import com.estaciona_ai.bookings.BookingRepository;
import com.estaciona_ai.bookings.BookingStatus;
import com.estaciona_ai.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverReviewService {

    private final DriverReviewRepository driverReviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DriverReviewMapper driverReviewMapper;

    @Transactional
    public DriverReviewResponse createReview(DriverReviewRequest request, UUID ownerId) {
        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + request.bookingId()));

        if (!booking.getGarage().getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("Apenas o dono da garagem pode avaliar o motorista desta reserva.");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new IllegalArgumentException("Você só pode avaliar o motorista após a conclusão da reserva.");
        }

        if (driverReviewRepository.existsByBookingId(request.bookingId())) {
            throw new IllegalArgumentException("Esta reserva já possui uma avaliação registrada para o motorista.");
        }

        // Conversão limpa usando MapStruct com múltiplos parâmetros
        DriverReviewEntity review = driverReviewMapper.toEntity(request, booking);

        return driverReviewMapper.toResponse(driverReviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public Page<DriverReviewResponse> getDriverReviews(UUID driverId, Pageable pageable) {
        if (!userRepository.existsById(driverId)) {
            throw new EntityNotFoundException("Motorista não encontrado com ID: " + driverId);
        }
        return driverReviewRepository.findByDriverIdOrderByCreatedAtDesc(driverId, pageable)
                .map(driverReviewMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public DriverRatingSummary getDriverRatingSummary(UUID driverId) {
        if (!userRepository.existsById(driverId)) {
            throw new EntityNotFoundException("Motorista não encontrado com ID: " + driverId);
        }

        Double avg = driverReviewRepository.findAverageRatingByDriverId(driverId).orElse(0.0);
        Long count = driverReviewRepository.countByDriverId(driverId);

        double roundedAvg = BigDecimal.valueOf(avg)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        return new DriverRatingSummary(driverId, roundedAvg, count);
    }
}