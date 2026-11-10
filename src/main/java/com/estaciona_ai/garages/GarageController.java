package com.estaciona_ai.garages;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/garages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Garagens", description = "Busca por localização e gerenciamento de vagas de estacionamento")
public class GarageController {

    private final GarageService garageService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Criar Garagem", description = "Cadastra uma nova garagem atribuída ao proprietário logado.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Garagem cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da vaga inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    public ResponseEntity<GarageResponse> createGarage(@Valid @RequestBody GarageRequest garageReq) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        GarageResponse createdGarage = garageService.createGarage(garageReq, ownerId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdGarage.garageId())
                .toUri();

        return ResponseEntity.created(location).body(createdGarage);
    }

    @Operation(summary = "Listar Garagens Disponíveis", description = "Retorna todas as garagens ativas na plataforma. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<GarageResponse>> getAllAvailableGarages() {
        return ResponseEntity.ok(garageService.getAllAvailableGarages());
    }

    @Operation(summary = "Listar Garagens do Proprietário", description = "Retorna todas as garagens pertencentes ao usuário logado.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de garagens retornada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/owner")
    public ResponseEntity<List<GarageResponse>> getGaragesByOwner() {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        List<GarageResponse> garages = garageService.getGaragesByOwner(ownerId);
        return ResponseEntity.ok(garages);
    }

    @Operation(summary = "Buscar Garagem por ID", description = "Obtém informações detalhadas de uma garagem específica pelo ID. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garagem encontrada"),
            @ApiResponse(responseCode = "404", description = "Garagem não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GarageResponse> getGarageById(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID id) {
        GarageResponse garageRes = garageService.getGarageById(id);
        return ResponseEntity.ok(garageRes);
    }

    @Operation(summary = "Buscar Garagens Próximas (Geolocalização)", description = "Busca paginada de garagens disponíveis em um raio de distância em Km a partir das coordenadas geográficas. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de garagens retornada")
    })
    @GetMapping("/nearby")
    public ResponseEntity<Page<GarageNearbyResponse>> getNearbyGarages(
            @Parameter(description = "Latitude inicial", example = "-23.561414") @RequestParam double lat,
            @Parameter(description = "Longitude inicial", example = "-46.655881") @RequestParam double lng,
            @Parameter(description = "Raio máximo de busca em Km", example = "10.0") @RequestParam(defaultValue = "10.0") double radius,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(garageService.findNearbyGarages(lat, lng, radius, pageable));
    }

    @Operation(summary = "Atualizar Garagem", description = "Atualiza os dados de uma vaga de garagem do proprietário.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garagem atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da vaga inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Garagem não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GarageResponse> updateGarageById(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID id,
            @Valid @RequestBody GarageRequest garageReq
    ) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        GarageResponse garageRes = garageService.updateGarageById(id, garageReq, ownerId);
        return ResponseEntity.ok(garageRes);
    }

    @Operation(summary = "Excluir Garagem", description = "Remove o cadastro de uma garagem pertencente ao proprietário.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Garagem excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Garagem não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarageById(
            @Parameter(description = "ID da Garagem", example = "a1111111-1111-1111-1111-111111111111") @PathVariable UUID id) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        garageService.deleteGarageById(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}