package com.estaciona_ai.bookings;

import com.estaciona_ai.garages.GarageEntity;
import com.estaciona_ai.garages.GarageRepository;
import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import com.estaciona_ai.vehicles.VehicleEntity;
import com.estaciona_ai.vehicles.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final GarageRepository garageRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingResponse createBooking(BookingRequest request, UUID driverId) {
        UserEntity driver = userRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com ID: " + driverId));

        GarageEntity garage = garageRepository.findById(request.garageId())
                .orElseThrow(() -> new EntityNotFoundException("Garagem não encontrada com ID: " + request.garageId()));

        VehicleEntity vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + request.vehicleId()));

        if (!vehicle.getOwner().getId().equals(driverId)) {
            throw new IllegalArgumentException("Este veículo não pertence ao motorista informado.");
        }

        if (!Boolean.TRUE.equals(garage.getAvailable())) {
            throw new IllegalArgumentException("Esta garagem não está disponível para reservas.");
        }

        if (request.startDateTime().isAfter(request.endDateTime()) || request.startDateTime().isEqual(request.endDateTime())) {
            throw new IllegalArgumentException("A data/hora final deve ser posterior à data/hora inicial.");
        }

        BigDecimal totalAmount = calculateTotalAmount(garage, request);

        BookingEntity booking = new BookingEntity();
        booking.setDriver(driver);
        booking.setGarage(garage);
        booking.setVehicle(vehicle);
        booking.setStartDateTime(request.startDateTime());
        booking.setEndDateTime(request.endDateTime());
        booking.setBookingType(request.bookingType());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PENDING);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }



    private BigDecimal calculateTotalAmount(GarageEntity garage, BookingRequest request) {
        if (request.bookingType() == BookingType.HOURLY) {
            if (garage.getPricePerHour() == null) {
                throw new IllegalArgumentException("Esta garagem não aceita reservas por hora.");
            }
            long hours = Duration.between(request.startDateTime(), request.endDateTime()).toHours();
            long effectiveHours = Math.max(hours, 1);
            return garage.getPricePerHour().multiply(BigDecimal.valueOf(effectiveHours));
        } else {
            if (garage.getPricePerDay() == null) {
                throw new IllegalArgumentException("Esta garagem não aceita reservas por dia.");
            }
            long days = ChronoUnit.DAYS.between(request.startDateTime().toLocalDate(), request.endDateTime().toLocalDate());
            long effectiveDays = Math.max(days, 1);
            return garage.getPricePerDay().multiply(BigDecimal.valueOf(effectiveDays));
        }
    }
}