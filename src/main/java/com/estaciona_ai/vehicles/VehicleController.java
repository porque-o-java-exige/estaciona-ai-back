package com.estaciona_ai.vehicles;

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
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Veículos", description = "Cadastro e gerenciamento de veículos pertencentes aos motoristas")
@SecurityRequirement(name = "bearerAuth")
public class VehicleController {

    private final VehicleService vehicleService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Cadastrar Veículo", description = "Cadastra um novo veículo vinculado automaticamente ao motorista autenticado via token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da placa ou formato do veículo inválidos"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente")
    })
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody VehicleRequest vehicleReq) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        VehicleResponse createdVehicle = vehicleService.createVehicle(vehicleReq, ownerId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVehicle.id())
                .toUri();

        return ResponseEntity.created(location).body(createdVehicle);
    }

    @Operation(summary = "Listar Meus Veículos", description = "Retorna todos os veículos cadastrados do motorista logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de veículos retornada"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente")
    })
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehiclesByOwner() {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    @Operation(summary = "Buscar Veículo por ID", description = "Busca detalhes de um veículo específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(
            @Parameter(description = "ID do Veículo", example = "6f87d720-11e1-45b4-a20e-f667b0961404") @PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @Operation(summary = "Atualizar Veículo", description = "Altera os dados de um veículo cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicleById(
            @Parameter(description = "ID do Veículo", example = "6f87d720-11e1-45b4-a20e-f667b0961404") @PathVariable UUID id,
            @Valid @RequestBody VehicleRequest vehicleReq
    ) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(vehicleService.updateVehicleById(id, vehicleReq, ownerId));
    }

    @Operation(summary = "Excluir Veículo", description = "Remove um veículo cadastrado do motorista.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleById(
            @Parameter(description = "ID do Veículo", example = "6f87d720-11e1-45b4-a20e-f667b0961404") @PathVariable UUID id) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        vehicleService.deleteVehicleById(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}