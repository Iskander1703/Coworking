package ru.iskander.tabaev.coworking.controllers.controllerAdvice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private Integer errorCode;
    private String message;
}
