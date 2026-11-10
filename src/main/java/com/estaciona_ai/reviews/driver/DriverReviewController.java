package com.estaciona_ai.reviews.driver;

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
@RequestMapping("/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Avaliações - Motorista", description = "Endpoints para os proprietários de garagens avaliarem os motoristas")
public class DriverReviewController {

    private final DriverReviewService driverReviewService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Avaliar Motorista", description = "Cria uma nova avaliação para o motorista referente a uma reserva concluída. Identidade do dono extraída do JWT.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da avaliação inválidos ou reserva não finalizada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping("/reviews")
    public ResponseEntity<DriverReviewResponse> createReview(
            @Valid @RequestBody DriverReviewRequest request
    ) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverReviewService.createReview(request, ownerId));
    }

    @Operation(summary = "Listar Avaliações do Motorista", description = "Retorna o histórico paginado de avaliações recebidas por um motorista. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de avaliações retornada com sucesso")
    })
    @GetMapping("/{driverId}/reviews")
    public ResponseEntity<Page<DriverReviewResponse>> getDriverReviews(
            @Parameter(description = "ID do Motorista", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID driverId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(driverReviewService.getDriverReviews(driverId, pageable));
    }

    @Operation(summary = "Obter Resumo de Nota do Motorista", description = "Retorna a média aritmética das notas e a quantidade total de avaliações de um motorista. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo da pontuação retornado com sucesso")
    })
    @GetMapping("/{driverId}/rating")
    public ResponseEntity<DriverRatingSummary> getDriverRatingSummary(
            @Parameter(description = "ID do Motorista", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID driverId
    ) {
        return ResponseEntity.ok(driverReviewService.getDriverRatingSummary(driverId));
    }
}