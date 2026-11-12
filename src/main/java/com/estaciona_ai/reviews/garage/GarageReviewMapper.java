package com.estaciona_ai.reviews.garage;

import com.estaciona_ai.bookings.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GarageReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "rating", source = "request.rating")
    @Mapping(target = "comment", source = "request.comment")
    @Mapping(target = "booking", source = "booking")
    @Mapping(target = "driver", source = "booking.driver")
    @Mapping(target = "garage", source = "booking.garage")
    GarageReviewEntity toEntity(GarageReviewRequest request, BookingEntity booking);

    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "garageId", source = "garage.id")
    @Mapping(target = "driverName", source = "driver.name")
    GarageReviewResponse toResponse(GarageReviewEntity entity);
}