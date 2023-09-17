package ru.iskander.tabaev.coworking.controllers.coworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.iskander.tabaev.coworking.controllers.room.dto.RoomResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CoworkingResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private List<RoomResponse> rooms;
}
