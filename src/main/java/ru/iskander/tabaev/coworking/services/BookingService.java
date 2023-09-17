package ru.iskander.tabaev.coworking.services;

import org.springframework.stereotype.Service;
import ru.iskander.tabaev.coworking.controllers.booking.dto.BookingCreateRequest;
import ru.iskander.tabaev.coworking.controllers.booking.dto.BookingCreateResponse;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;
import ru.iskander.tabaev.coworking.mappers.BookingMapper;
import ru.iskander.tabaev.coworking.models.Booking;
import ru.iskander.tabaev.coworking.models.Coworking;
import ru.iskander.tabaev.coworking.models.Room;
import ru.iskander.tabaev.coworking.repositories.BookingRepository;
import ru.iskander.tabaev.coworking.repositories.CoworkingRepository;
import ru.iskander.tabaev.coworking.repositories.RoomRepository;
import ru.iskander.tabaev.coworking.services.utils.DateUtils;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final CoworkingRepository coworkingRepository;

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    public BookingService(CoworkingRepository coworkingRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.coworkingRepository = coworkingRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }


    public BookingCreateResponse createBooking(Long coworkingId,
                                               Long roomId,
                                               BookingCreateRequest request) throws WebResourceNotFoundException, WebServiceException {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new WebServiceException("Invalid date interval. The interval should have been a multiple of half an hour");
        } else {
            if (!DateUtils.checkDateInterval(request.getStartTime(), request.getEndTime())) {
                throw new WebServiceException("Invalid date interval. The interval should have been a multiple of half an hour");
            }
            if (checkRoomIsFree(roomId, request.getStartTime(), request.getEndTime())) {
                throw new WebServiceException("Room is not free in choose interval");
            }
        }

        Coworking coworking = coworkingRepository.findById(coworkingId)
                .orElseThrow(() -> new WebResourceNotFoundException("Coworking was not found"));

        Room room = roomRepository.findByCoworkingIdAndId(coworkingId, roomId)
                .orElseThrow(() -> new WebResourceNotFoundException("Room was not found"));


        Booking booking = BookingMapper.INSTANCE.fromBookingCreateRequestToBooking(request);
        room.getBookings().add(booking);
        booking.setRoom(room);
        return BookingMapper.INSTANCE.fromBookingToBookingCreateRequest(bookingRepository.save(booking));
    }

    private boolean checkRoomIsFree(Long roomId, LocalDateTime startDate, LocalDateTime endDate) {
        return roomRepository.getAvailableRoom(roomId, startDate, endDate).isEmpty();
    }
}
