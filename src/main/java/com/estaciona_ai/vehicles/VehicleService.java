package com.estaciona_ai.vehicles;

import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    // Create vehicle method
    @Transactional
    public VehicleResponse createVehicle(VehicleRequest vehicleReq, UUID ownerId) {

        UserEntity owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado"));

        if (vehicleRepository.existsByLicensePlate(vehicleReq.licensePlate())) {
            throw new IllegalArgumentException("já existe um veículo cadastrado com essa placa");
        }
        VehicleEntity vehicle = vehicleMapper.toEntity(vehicleReq);
        vehicle.setOwner(owner);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    // Get vehicle by id method
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com o ID: " + vehicleId));
    }

    // Get vehicles by ower method
    @Transactional(readOnly = true)
    public List<VehicleResponse> getVehiclesByOwner(UUID ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + ownerId);
        }
        return vehicleMapper.toResponseList(vehicleRepository.findByOwnerId(ownerId));
    }


}