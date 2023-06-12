package com.janluk.meeteevent.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class LoginAlreadyTaken extends RuntimeException{
    public LoginAlreadyTaken(String message) {
        super(message);
    }
}
