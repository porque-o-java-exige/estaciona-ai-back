package com.estaciona_ai.garages;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    GarageEntity toEntity(GarageRequest garageReq);

    @Mapping(source = "id", target = "garageId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(target = "price", expression = "java(garageEnt.getPricePerHour() != null ? garageEnt.getPricePerHour() : garageEnt.getPricePerDay())")
    GarageResponse toResponse(GarageEntity garageEnt);

    List<GarageResponse> toResponseList(List<GarageEntity> garages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(GarageRequest garageReq, @MappingTarget GarageEntity garageEntity);
}