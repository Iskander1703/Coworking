package ru.iskander.tabaev.coworking.controllers.booking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingCreateRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
