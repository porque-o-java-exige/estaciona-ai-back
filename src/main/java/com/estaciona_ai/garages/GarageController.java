package com.estaciona_ai.garages;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class GarageController {

    private final GarageService garageService;

    @PostMapping
    public ResponseEntity<GarageResponse> createGarage(
            @Valid @RequestBody GarageRequest garageReq,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        GarageResponse createdGarage = garageService.createGarage(garageReq, ownerId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdGarage.garageId())
                .toUri();
        return ResponseEntity.created(location).body(createdGarage);
    }

    @GetMapping
    public ResponseEntity<List<GarageResponse>> getAllAvailableGarages() {
        return ResponseEntity.ok(garageService.getAllAvailableGarages());
    }

    @GetMapping("/owner")
    public ResponseEntity<List<GarageResponse>> getGaragesByOwner(
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        List<GarageResponse> garages = garageService.getGaragesByOwner(ownerId);
        return ResponseEntity.ok(garages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GarageResponse> getGarageById(
            @PathVariable UUID id
    ) {
        GarageResponse garageRes = garageService.getGarageById(id);
        return ResponseEntity.ok(garageRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageResponse> updateGarageById(
            @PathVariable UUID id,
            @Valid @RequestBody GarageRequest garageReq,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        GarageResponse garageRes =
                garageService.updateGarageById(id, garageReq, ownerId);

        return ResponseEntity.ok(garageRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarageById(
            @PathVariable UUID id,
            @RequestHeader("x-user-id") UUID ownerId
    ) {
        garageService.deleteGarageById(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}
