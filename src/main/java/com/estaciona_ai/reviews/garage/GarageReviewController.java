package com.estaciona_ai.reviews.garage;

import com.estaciona_ai.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Avaliações - Garagem", description = "Endpoints para os motoristas avaliarem as garagens onde estacionaram")
public class GarageReviewController {

    private final GarageReviewService garageReviewService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Avaliar Garagem", description = "Registra uma avaliação do motorista sobre a vaga de garagem utilizada. Identidade do motorista extraída do JWT.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação da garagem criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou reserva não finalizada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/reviews")
    public ResponseEntity<GarageReviewResponse> createReview(
            @Valid @RequestBody GarageReviewRequest request
    ) {
        UUID driverId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(garageReviewService.createReview(request, driverId));
    }

    @Operation(summary = "Listar Avaliações da Garagem", description = "Retorna o histórico paginado de avaliações de uma garagem. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de avaliações da garagem retornada com sucesso")
    })
    @GetMapping("/{garageId}/reviews")
    public ResponseEntity<Page<GarageReviewResponse>> getGarageReviews(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID garageId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(garageReviewService.getGarageReviews(garageId, pageable));
    }

    @Operation(summary = "Obter Média de Avaliações da Garagem", description = "Retorna a nota média e o número total de avaliações recebidas por uma garagem. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo da nota retornado com sucesso")
    })
    @GetMapping("/{garageId}/rating")
    public ResponseEntity<GarageRatingSummary> getGarageRatingSummary(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID garageId
    ) {
        return ResponseEntity.ok(garageReviewService.getGarageRatingSummary(garageId));
    }
}