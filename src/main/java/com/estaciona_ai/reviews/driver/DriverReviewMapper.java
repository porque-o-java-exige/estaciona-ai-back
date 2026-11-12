package com.estaciona_ai.reviews.driver;

import com.estaciona_ai.bookings.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "rating", source = "request.rating")
    @Mapping(target = "comment", source = "request.comment")
    @Mapping(target = "booking", source = "booking")
    @Mapping(target = "owner", source = "booking.garage.owner")
    @Mapping(target = "driver", source = "booking.driver")
    DriverReviewEntity toEntity(DriverReviewRequest request, BookingEntity booking);

    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "ownerName", source = "owner.name")
    DriverReviewResponse toResponse(DriverReviewEntity entity);
}