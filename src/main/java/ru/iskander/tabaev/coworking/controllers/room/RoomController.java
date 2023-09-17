package ru.iskander.tabaev.coworking.controllers.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iskander.tabaev.coworking.controllers.room.dto.RoomCreateRequest;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;
import ru.iskander.tabaev.coworking.services.RoomService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/coworkings/{coworkingId}/rooms")
@Tag(name = "Комнаты", description = "Api для управления комнатами в кворкингах")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Получить комнаты в коворкинге", description = "Получение списка комнат в указанном коворкинге.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный интервал даты или вместимости")
    })
    public ResponseEntity getRoomsByCoworking(
            @PathVariable Long coworkingId,
            @RequestParam(name = "minCapacity", required = false) Integer minCapacity,
            @RequestParam(name = "beginDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
            @RequestParam(name = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) throws WebResourceNotFoundException, WebServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomsByCoworking(coworkingId, minCapacity, beginDate, endDate));
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "Получить комнату по ID", description = "Получение информации о комнате в указанном коворкинге по ее ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "404", description = "Коворкинг или комната не найдены")
    })
    public ResponseEntity getRoomByCoworkingAndRoomId(
            @PathVariable Long coworkingId,
            @PathVariable Long roomId) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomByCoworkingIdAndRoomId(coworkingId, roomId));
    }

    @PostMapping
    @Operation(summary = "Создать новую комнату", description = "Создание новой комнаты в указанном коворкинге.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Комната успешно создана"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity createRoom(
            @PathVariable Long coworkingId,
            @RequestBody RoomCreateRequest request) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.createRoom(coworkingId, request));
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "Удалить комнату", description = "Удаление комнаты в указанном коворкинге по ее ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Коворкинг или комната не найдены")
    })
    public ResponseEntity deleteRoom(
            @PathVariable Long coworkingId,
            @PathVariable Long roomId) throws WebResourceNotFoundException {
        roomService.deleteRoom(coworkingId, roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "Обновить информацию о комнате", description = "Обновление информации о комнате в указанном коворкинге по ее ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Коворкинг или комната не найдены")
    })
    public ResponseEntity updateRoom(
            @PathVariable Long coworkingId,
            @PathVariable Long roomId,
            @RequestBody RoomCreateRequest request) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(coworkingId, roomId, request));
    }
}
