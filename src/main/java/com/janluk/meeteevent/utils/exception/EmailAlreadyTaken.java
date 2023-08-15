package com.janluk.meeteevent.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailAlreadyTaken extends RuntimeException{
    public EmailAlreadyTaken(String message) {
        super(message);
    }
}
