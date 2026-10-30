package com.estaciona_ai.auth;

import com.estaciona_ai.users.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    @Mapping(source = "token", target = "token")
    @Mapping(source = "tokenType", target = "tokenType")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    LoginResponse toLoginResponse(UserEntity user, String token, String tokenType);
}