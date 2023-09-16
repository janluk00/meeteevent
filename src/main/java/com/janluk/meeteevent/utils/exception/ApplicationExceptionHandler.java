package com.janluk.meeteevent.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ExceptionAPI> handleResourceNotFoundException(ResourceNotFound rnf) {
        return new ResponseEntity<>(
                new ExceptionAPI(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        rnf.getLocalizedMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EmailAlreadyTaken.class)
    public ResponseEntity<ExceptionAPI> handleEmailAlreadyTaken(EmailAlreadyTaken eat) {
        return new ResponseEntity<>(
                new ExceptionAPI(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST,
                        eat.getLocalizedMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(LoginAlreadyTaken.class)
    public ResponseEntity<ExceptionAPI> handleLoginAlreadyTaken(LoginAlreadyTaken lat) {
        return new ResponseEntity<>(
                new ExceptionAPI(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST,
                        lat.getLocalizedMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
