package ru.iskander.tabaev.coworking.controllers.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.iskander.tabaev.coworking.controllers.controllerAdvice.dto.ExceptionResponse;
import ru.iskander.tabaev.coworking.exceptions.WebResourceNotFoundException;
import ru.iskander.tabaev.coworking.exceptions.WebServiceException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(WebResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(WebResourceNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getErrorCustomMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<ExceptionResponse> handleException(WebServiceException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getErrorCustomMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
