package com.estaciona_ai.garages;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    GarageEntity toEntity(GarageRequest garageReq);

    @Mapping(source = "owner.id", target = "ownerId")
    GarageResponse toResponse(GarageEntity garageEnt);

    List<GarageResponse> toResponseList(List<GarageEntity> garages);

    void updateEntityFromDto(GarageRequest garageReq, @MappingTarget GarageEntity garageEntity);
}
