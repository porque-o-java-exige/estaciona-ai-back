package com.estaciona_ai.bookings;

import com.estaciona_ai.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reservas", description = "Criação, busca e cancelamento de reservas de vagas")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Criar Reserva", description = "Solicita uma nova reserva de garagem vinculada ao motorista autenticado via JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso com status PENDING"),
            @ApiResponse(responseCode = "400", description = "Conflito de horários ou dados da reserva inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
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

    @Operation(summary = "Listar Minhas Reservas", description = "Retorna todas as reservas efetuadas pelo motorista logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas do motorista retornada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.getBookingsByDriver(driverId));
    }

    @Operation(summary = "Buscar Reserva por ID", description = "Obtém dados de uma reserva específica pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @Parameter(description = "ID da Reserva", example = "9b07e730-23e1-47b4-a20e-f667b0961309") @PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @Operation(summary = "Listar Reservas de uma Garagem", description = "Retorna todas as reservas de uma garagem do proprietário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas da garagem retornada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (usuário não é o proprietário desta garagem)")
    })
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByGarage(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID garageId) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.getBookingsByGarage(garageId, ownerId));
    }

    @Operation(summary = "Cancelar Reserva", description = "Muda o status da reserva para CANCELLED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @Parameter(description = "ID da Reserva", example = "9b07e730-23e1-47b4-a20e-f667b0961309") @PathVariable UUID id) {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(bookingService.cancelBooking(id, driverId));
    }
}