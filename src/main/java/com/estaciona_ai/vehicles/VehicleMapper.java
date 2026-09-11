package com.estaciona_ai.vehicles;

import com.estaciona_ai.vehicles.VehicleEntity;
import com.estaciona_ai.vehicles.VehicleRequest;
import com.estaciona_ai.vehicles.VehicleResponse;
import com.estaciona_ai.vehicles.VehicleEntity;
import com.estaciona_ai.vehicles.VehicleRequest;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleEntity toEntity(VehicleRequest vehicleReq);
    //Entity to response
    @Mapping(source = "owner.id", target = "ownerId")
    VehicleResponse toResponse(VehicleEntity vehicleEnt);

    List<VehicleResponse> toResponseList(List<VehicleEntity> vehicles);

    void updateEntityFromDto(VehicleRequest vehicleReq, @MappingTarget VehicleEntity vehicleEntity);
}
