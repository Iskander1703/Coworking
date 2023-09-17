package ru.iskander.tabaev.coworking.exceptions;

import lombok.Getter;

@Getter
public class WebServiceException extends Exception{
    private String errorCustomMessage;

    public WebServiceException(String errorMessage) {
        this.errorCustomMessage = errorMessage;
    }
}

