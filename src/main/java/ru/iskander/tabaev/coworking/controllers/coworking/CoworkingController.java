package ru.iskander.tabaev.coworking.controllers.coworking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iskander.tabaev.coworking.controllers.coworking.dto.CoworkingCreateRequest;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.services.BookingService;
import ru.iskander.tabaev.coworking.services.CoworkingService;
import ru.iskander.tabaev.coworking.services.RoomService;

@RestController
@RequestMapping("/api/coworkings")
@Tag(name = "Коворкинги", description = "Api для управления коворкингами")
public class CoworkingController {

    private final CoworkingService coworkingService;

    @Autowired
    public CoworkingController(CoworkingService coworkingService, RoomService roomService, BookingService bookingService) {
        this.coworkingService = coworkingService;
    }

    @GetMapping
    @Operation(summary = "Получить весь список кворкингов", description = "Получение списка всех коворкингов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
    })
    public ResponseEntity getAllCoworkings() {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.getAllCoworkings());
    }

    @GetMapping("/{coworkingId}")
    @Operation(summary = "Получить коворкинг по ID", description = "Получение информации о коворкинге по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity getCoworkingById(
            @Parameter(description = "ID коворкинга", required = true) @PathVariable Long coworkingId) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.getCoworkingById(coworkingId));
    }

    @DeleteMapping("/{coworkingId}")
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

    @PutMapping("/{coworkingId}")
    @Operation(summary = "Обновить информацию о коворкинге", description = "Обновление информации о коворкинге по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Коворкинг не найден")
    })
    public ResponseEntity updateCoworking(
            @Parameter(description = "ID коворкинга", required = true) @PathVariable Long coworkingId,
            @RequestBody CoworkingCreateRequest coworkingCreateRequest) throws WebResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(coworkingService.updateCoworking(coworkingId, coworkingCreateRequest));
    }


}
