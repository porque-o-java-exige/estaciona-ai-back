package com.estaciona_ai.vehicles;

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
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;
    private final SecurityUtils securityUtils;

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

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehiclesByOwner() {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicleById(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleRequest vehicleReq
    ) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(vehicleService.updateVehicleById(id, vehicleReq, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable UUID id) {
        UUID ownerId = securityUtils.getAuthenticatedUserId();
        vehicleService.deleteVehicleById(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}