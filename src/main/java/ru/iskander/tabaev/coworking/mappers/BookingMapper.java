package ru.iskander.tabaev.coworking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.BookingCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.BookingCreateResponse;
import ru.iskander.tabaev.coworking.models.Booking;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking fromBookingCreateRequestToBooking(BookingCreateRequest request);

    BookingCreateResponse fromBookingToBookingCreateRequest(Booking booking);

}
