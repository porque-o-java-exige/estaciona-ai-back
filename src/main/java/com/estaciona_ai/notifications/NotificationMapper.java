package com.estaciona_ai.notifications;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    NotificationResponse toResponse(NotificationEntity entity);

    List<NotificationResponse> toResponseList(List<NotificationEntity> entities);
}