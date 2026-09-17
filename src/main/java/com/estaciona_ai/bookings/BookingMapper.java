package com.estaciona_ai.bookings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "garage.id", target = "garageId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    BookingResponse toResponse(BookingEntity booking);

    List<BookingResponse> toResponseList(List<BookingEntity> bookings);
}