package com.estaciona_ai.reviews.garage;

import com.estaciona_ai.bookings.BookingEntity;
import com.estaciona_ai.bookings.BookingRepository;
import com.estaciona_ai.bookings.BookingStatus;
import com.estaciona_ai.garages.GarageRepository;
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
public class GarageReviewService {

    private final GarageReviewRepository garageReviewRepository;
    private final BookingRepository bookingRepository;
    private final GarageRepository garageRepository;
    private final GarageReviewMapper garageReviewMapper;

    @Transactional
    public GarageReviewResponse createReview(GarageReviewRequest request, UUID driverId) {
        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + request.bookingId()));

        if (!booking.getDriver().getId().equals(driverId)) {
            throw new IllegalArgumentException("Apenas o motorista desta reserva pode avaliar a garagem.");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new IllegalArgumentException("Você só pode avaliar a garagem após a conclusão da reserva.");
        }

        if (garageReviewRepository.existsByBookingId(request.bookingId())) {
            throw new IllegalArgumentException("Esta reserva já possui uma avaliação registrada para a garagem.");
        }

        // Conversão limpa usando MapStruct com múltiplos parâmetros
        GarageReviewEntity review = garageReviewMapper.toEntity(request, booking);

        return garageReviewMapper.toResponse(garageReviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public Page<GarageReviewResponse> getGarageReviews(UUID garageId, Pageable pageable) {
        if (!garageRepository.existsById(garageId)) {
            throw new EntityNotFoundException("Garagem não encontrada com ID: " + garageId);
        }
        return garageReviewRepository.findByGarageIdOrderByCreatedAtDesc(garageId, pageable)
                .map(garageReviewMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public GarageRatingSummary getGarageRatingSummary(UUID garageId) {
        if (!garageRepository.existsById(garageId)) {
            throw new EntityNotFoundException("Garagem não encontrada com ID: " + garageId);
        }

        Double avg = garageReviewRepository.findAverageRatingByGarageId(garageId).orElse(0.0);
        Long count = garageReviewRepository.countByGarageId(garageId);

        double roundedAvg = BigDecimal.valueOf(avg)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        return new GarageRatingSummary(garageId, roundedAvg, count);
    }
}