package ru.iskander.tabaev.coworking.controllers.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateRequest {

    private String name;
    private Integer maxCapacity;
    private String description;
}
