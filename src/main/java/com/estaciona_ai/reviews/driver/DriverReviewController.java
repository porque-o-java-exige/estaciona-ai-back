package com.estaciona_ai.reviews.driver;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DriverReviewController {

    private final DriverReviewService driverReviewService;

    // Criar avaliação para um motorista (Dono da Garagem -> Motorista)
    @PostMapping("/reviews")
    public ResponseEntity<DriverReviewResponse> createReview(
            @Valid @RequestBody DriverReviewRequest request,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverReviewService.createReview(request, ownerId));
    }

    // Listar histórico de avaliações recebidas por um motorista
    @GetMapping("/{driverId}/reviews")
    public ResponseEntity<Page<DriverReviewResponse>> getDriverReviews(
            @PathVariable UUID driverId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(driverReviewService.getDriverReviews(driverId, pageable));
    }

    // Obter nota média e contagem total de avaliações do motorista
    @GetMapping("/{driverId}/rating")
    public ResponseEntity<DriverRatingSummary> getDriverRatingSummary(
            @PathVariable UUID driverId
    ) {
        return ResponseEntity.ok(driverReviewService.getDriverRatingSummary(driverId));
    }
}