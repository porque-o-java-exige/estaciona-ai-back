package com.estaciona_ai.vehicles;

import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            UserRepository userRepository
    ) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public VehicleResponse createVehicle(
            VehicleRequest dto,
            UUID userId
    ) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        VehicleEntity vehicle = new VehicleEntity();

        vehicle.setUser(user);
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());

        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponse(savedVehicle);
    }

    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleResponse::new)
                .toList();
    }

    public VehicleResponse getVehicleById(UUID id) {

        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Veículo não encontrado"));

        return new VehicleResponse(vehicle);
    }

    public VehicleResponse updateVehicle(
            UUID id,
            VehicleRequest dto
    ) {

        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Veículo não encontrado"));

        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());

        VehicleEntity updatedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponse(updatedVehicle);
    }

    public void deleteVehicle(UUID id) {

        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Veículo não encontrado"));

        vehicleRepository.delete(vehicle);
    }
}