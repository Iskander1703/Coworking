package ru.iskander.tabaev.coworking.controllers.coworking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingCreateRequest;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.RoomCreateRequest;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;
import ru.iskander.tabaev.coworking.services.CoworkingService;
import ru.iskander.tabaev.coworking.services.RoomService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/coworkings")
@Tag(name = "Коворкинг", description = "Api для коворкингов")
public class CoworkingController {

    private final CoworkingService coworkingService;

    private final RoomService roomService;


    @Autowired
    public CoworkingController(CoworkingService coworkingService, RoomService roomService) {
        this.coworkingService = coworkingService;
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Получить весь список кворкингов", description = "Получение списка всех коворкингов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
    })
    public ResponseEntity getAllCoworkings() {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.getAllCoworkings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить коворкинг по ID", description = "Получение информации о коворкинге по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity getCoworkingById(
            @Parameter(description = "ID коворкинга", required = true) @PathVariable Long coworkingId) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.getCoworkingById(coworkingId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить коворкинг по ID", description = "Удаление коворкинга по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity deleteCoworkingById(
            @Parameter(description = "ID коворкинга", required = true) @PathVariable Long coworkingId) throws WebResourceNotFoundException {
        coworkingService.deleteCoworkingById(coworkingId);
        return new ResponseEntity<>("Deleted coworking successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @Operation(summary = "Создать новый коворкинг", description = "Создание нового коворкинга")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное создание"),
    })
    public ResponseEntity createCoworking(
            @RequestBody CoworkingCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coworkingService.createCoworking(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о коворкинге", description = "Обновление информации о коворкинге по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity updateCoworking(
            @Parameter(description = "ID коворкинга", required = true) @PathVariable Long id,
            @RequestBody CoworkingCreateRequest coworkingCreateRequest) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.updateCoworking(id, coworkingCreateRequest));
    }

    @GetMapping("{coworkingId}/rooms")
    @Operation(summary = "Получить комнаты в коворкинге", description = "Получение списка комнат в указанном коворкинге.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity getRoomsByCoworking(
            @PathVariable Long coworkingId,
            @RequestParam(name = "minCapacity", required = false) Integer minCapacity,
            @RequestParam(name = "beginDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
            @RequestParam(name = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) throws WebResourceNotFoundException, WebServiceException {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomsByCoworking(coworkingId, minCapacity, beginDate, endDate));
    }

    @GetMapping("{coworkingId}/rooms/{roomId}")
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

    @PostMapping("{coworkingId}/rooms")
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

    @DeleteMapping("{coworkingId}/rooms/{roomId}")
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

    @PutMapping("{coworkingId}/rooms/{roomId}")
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
