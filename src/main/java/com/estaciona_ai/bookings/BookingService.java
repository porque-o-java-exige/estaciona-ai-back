package com.estaciona_ai.bookings;

import com.estaciona_ai.chat.room.ChatRoomEntity;
import com.estaciona_ai.chat.room.ChatRoomRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final GarageRepository garageRepository;
    private final VehicleRepository vehicleRepository;
    private final ChatRoomRepository chatRoomRepository;
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

        boolean hasConflict = bookingRepository.existsOverlappingBooking(
                request.garageId(),
                request.startDateTime(),
                request.endDateTime()
        );

        if (hasConflict) {
            throw new IllegalArgumentException("A garagem já possui uma reserva confirmada ou pendente para este horário.");
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

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Vincula a reserva a uma sala existente ou cria uma nova sala
        linkOrCreateChatRoom(savedBooking, driver, garage);

        return bookingMapper.toResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByDriver(UUID driverId) {
        return bookingMapper.toResponseList(bookingRepository.findByDriverId(driverId));
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .map(bookingMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + bookingId));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByGarage(UUID garageId, UUID ownerId) {
        GarageEntity garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new EntityNotFoundException("Garagem não encontrada"));

        if (!garage.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("Você não tem permissão para ver as reservas desta garagem.");
        }

        return bookingMapper.toResponseList(bookingRepository.findByGarageId(garageId));
    }

    @Transactional
    public BookingResponse cancelBooking(UUID bookingId, UUID driverId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com ID: " + bookingId));

        if (!booking.getDriver().getId().equals(driverId)) {
            throw new IllegalArgumentException("Você não tem permissão para cancelar esta reserva.");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Esta reserva já está cancelada.");
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalArgumentException("Não é possível cancelar uma reserva que já foi finalizada.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    private BigDecimal calculateTotalAmount(GarageEntity garage, BookingRequest request) {
        long minutes = Duration.between(request.startDateTime(), request.endDateTime()).toMinutes();

        if (request.bookingType() == BookingType.HOURLY) {
            if (garage.getPricePerHour() == null) {
                throw new IllegalArgumentException("Esta garagem não aceita reservas por hora.");
            }
            long hours = (long) Math.ceil((double) minutes / 60.0);
            long effectiveHours = Math.max(hours, 1);
            return garage.getPricePerHour().multiply(BigDecimal.valueOf(effectiveHours));
        } else {
            if (garage.getPricePerDay() == null) {
                throw new IllegalArgumentException("Esta garagem não aceita reservas por dia.");
            }
            long days = (long) Math.ceil((double) minutes / (60.0 * 24.0));
            long effectiveDays = Math.max(days, 1);
            return garage.getPricePerDay().multiply(BigDecimal.valueOf(effectiveDays));
        }
    }

    private void linkOrCreateChatRoom(BookingEntity booking, UserEntity driver, GarageEntity garage) {
        Optional<ChatRoomEntity> existingRoom = chatRoomRepository
                .findByGarageIdAndDriverId(garage.getId(), driver.getId());

        if (existingRoom.isPresent()) {
            ChatRoomEntity room = existingRoom.get();
            room.setBooking(booking);
            chatRoomRepository.save(room);
        } else {
            ChatRoomEntity newRoom = new ChatRoomEntity();
            newRoom.setGarage(garage);
            newRoom.setDriver(driver);
            newRoom.setOwner(garage.getOwner());
            newRoom.setBooking(booking);
            chatRoomRepository.save(newRoom);
        }
    }
}