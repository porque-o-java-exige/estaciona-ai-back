package com.estaciona_ai.vehicles;

import com.estaciona_ai.vehicles.VehicleEntity;
import com.estaciona_ai.vehicles.VehicleRequest;
import com.estaciona_ai.vehicles.VehicleResponse;
import com.estaciona_ai.vehicles.VehicleEntity;
import com.estaciona_ai.vehicles.VehicleRequest;
import org.mapstruct.MappingTarget;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleEntity toEntity(VehicleRequest vehicleReq);

    List<VehicleResponse> toResponseList(List<VehicleEntity> entities);
    //Entity to response
    VehicleResponse toResponse(VehicleEntity vehicleEnt);

    void updateEntityFromDto(VehicleRequest vehicleReq, @MappingTarget VehicleEntity vehicleEntity);
}
