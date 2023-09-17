package ru.iskander.tabaev.coworking.controllers.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iskander.tabaev.coworking.controllers.booking.dto.BookingCreateRequest;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;
import ru.iskander.tabaev.coworking.services.BookingService;

@RestController
@RequestMapping("/api/coworkings/{coworkingId}/rooms/{roomId}/booking")
@Tag(name = "Бронирования", description = "Api для управления бронирования в комнатах")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Бронирование комнаты на определенное время",
            description = "Бронирование комнаты в опредленное время в опредленном кворкинге")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Бронирование успешно создано"),
            @ApiResponse(responseCode = "404", description = "Коворкинг или комната не найдены"),
            @ApiResponse(responseCode = "400", description = "Неверный интервал даты")
    })
    public ResponseEntity createBooking(
            @PathVariable Long coworkingId,
            @PathVariable Long roomId,
            @RequestBody BookingCreateRequest request) throws WebResourceNotFoundException, WebServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.createBooking(coworkingId, roomId, request));
    }
}
