package ru.iskander.tabaev.coworking.controllers.coworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoworkingCreateRequest {
    private String name;
    private String address;
    private String description;
}
