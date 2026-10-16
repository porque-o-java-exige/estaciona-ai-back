package com.estaciona_ai.reviews.garage;

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
@RequestMapping("/garages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GarageReviewController {

    private final GarageReviewService garageReviewService;

    // Criar avaliação para uma garagem (Motorista -> Garagem)
    @PostMapping("/reviews")
    public ResponseEntity<GarageReviewResponse> createReview(
            @Valid @RequestBody GarageReviewRequest request,
            @RequestHeader("x-user-id") UUID driverId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(garageReviewService.createReview(request, driverId));
    }

    // Listar histórico de avaliações recebidas por uma garagem
    @GetMapping("/{garageId}/reviews")
    public ResponseEntity<Page<GarageReviewResponse>> getGarageReviews(
            @PathVariable UUID garageId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(garageReviewService.getGarageReviews(garageId, pageable));
    }

    // Obter nota média e contagem total de avaliações da garagem
    @GetMapping("/{garageId}/rating")
    public ResponseEntity<GarageRatingSummary> getGarageRatingSummary(
            @PathVariable UUID garageId
    ) {
        return ResponseEntity.ok(garageReviewService.getGarageRatingSummary(garageId));
    }
}