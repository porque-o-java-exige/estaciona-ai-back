package com.estaciona_ai.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //Request to entity
    UserEntity toEntity(UserRequest userReq);
    //Response to list
    List<UserResponse> toResponseList(List<UserEntity> entities);
    //Entity to response
    UserResponse toResponse(UserEntity userEnt);

    void updateEntityFromDto(UserRequest userReq, @MappingTarget UserEntity userEntity);

}