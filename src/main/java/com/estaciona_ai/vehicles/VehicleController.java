package com.estaciona_ai.vehicles;

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
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @RequestBody VehicleRequest vehicleReq, @RequestHeader("x-user-id") UUID ownerId) {
        VehicleResponse createdVehicle = vehicleService.createVehicle(vehicleReq, ownerId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVehicle.id()) // Chamada do record id()
                .toUri();

        return ResponseEntity.created(location).body(createdVehicle);
    }

    // Retorna os veículos do usuário autenticado
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehiclesByOwner(
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        List<VehicleResponse> vehicles = vehicleService.getVehiclesByOwner(ownerId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id) {
        VehicleResponse vehicleRes = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicleRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicleById(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleRequest vehicleReq, // @Valid adicionado
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        VehicleResponse vehicleRes = vehicleService.updateVehicleById(id, vehicleReq, ownerId);
        return ResponseEntity.ok(vehicleRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleById(
            @PathVariable UUID id,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        vehicleService.deleteVehicleById(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}