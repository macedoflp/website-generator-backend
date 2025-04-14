package com.WebGenerator.App.api.controller.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WebSiteNotFoundException extends RuntimeException {

    public WebSiteNotFoundException(String message) {
        super(message);
    }

    public WebSiteNotFoundException() {
        super("Site não encontrado!");
    }
}

