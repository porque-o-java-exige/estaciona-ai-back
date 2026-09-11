package com.estaciona_ai.vehicles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {

    boolean existsByLicensePlate(String licensePlate);
    List<VehicleEntity> findByOwnerId(UUID ownerId);
}