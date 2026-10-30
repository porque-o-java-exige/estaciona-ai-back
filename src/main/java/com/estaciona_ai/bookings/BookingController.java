package com.estaciona_ai.bookings;

import com.estaciona_ai.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final SecurityUtils securityUtils;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        BookingResponse created = bookingService.createBooking(request, driverId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.bookingId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.getBookingsByDriver(driverId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByGarage(@PathVariable UUID garageId) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.getBookingsByGarage(garageId, ownerId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable UUID id) {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.cancelBooking(id, driverId));
    }
}