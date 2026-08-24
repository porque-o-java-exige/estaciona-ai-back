package com.estaciona_ai.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //Request to entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    UserEntity toEntity(UserRequest userReq);
    //Response to list
    List<UserResponse> toResponseList(List<UserEntity> entities);
    //Entity to response
    UserResponse toResponse(UserEntity userEnt);

    void updateEntityFromDto(UserRequest userReq, @MappingTarget UserEntity userEntity);

}