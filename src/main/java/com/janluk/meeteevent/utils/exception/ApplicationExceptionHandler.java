package com.janluk.meeteevent.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ExceptionAPI> handleException(ResourceNotFound rnf) {
        return new ResponseEntity<>(
                new ExceptionAPI(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        rnf.getLocalizedMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    // TODO: HANDLE MORE EXCEPTIONS
}
