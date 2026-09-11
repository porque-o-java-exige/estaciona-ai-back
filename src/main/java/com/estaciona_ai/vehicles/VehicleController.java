package com.estaciona_ai.vehicles;

import com.estaciona_ai.vehicles.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin("*")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @Valid @RequestBody VehicleRequest vehicleReq,
            @RequestParam UUID userId
    ) {
        VehicleResponse createdVehicle =
                vehicleService.createVehicle(vehicleReq, userId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVehicle.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdVehicle);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllvehicles(){
        List<VehicleResponse> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id){
        VehicleResponse vehicleRes = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicleRes);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable UUID id, @RequestBody VehicleRequest vehicleReq){
        VehicleResponse vehicleRes = vehicleService.updateVehicle(id, vehicleReq);
        return ResponseEntity.ok(vehicleRes);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
