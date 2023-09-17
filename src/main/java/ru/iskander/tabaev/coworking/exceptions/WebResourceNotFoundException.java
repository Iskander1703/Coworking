package ru.iskander.tabaev.coworking.exceptions;

import lombok.Getter;

@Getter
public class WebResourceNotFoundException extends Exception{
    private String errorCustomMessage;

    public WebResourceNotFoundException(String errorMessage) {
        this.errorCustomMessage = errorMessage;
    }
}
