package ru.iskander.tabaev.coworking.controllers.coworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingCreateResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
