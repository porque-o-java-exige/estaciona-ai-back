package com.estaciona_ai.bookings;

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

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request,
            @RequestHeader("x-user-id") UUID driverId
    ) {
        BookingResponse created = bookingService.createBooking(request, driverId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.bookingId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @RequestHeader("x-user-id") UUID driverId
    ) {
        return ResponseEntity.ok(bookingService.getBookingsByDriver(driverId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByGarage(
            @PathVariable UUID garageId,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        return ResponseEntity.ok(bookingService.getBookingsByGarage(garageId, ownerId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable UUID id,
            @RequestHeader("x-user-id") UUID driverId
    ) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, driverId));
    }


}