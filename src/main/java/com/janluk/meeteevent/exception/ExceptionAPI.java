package com.janluk.meeteevent.exception;

import org.springframework.http.HttpStatus;

public record ExceptionAPI(Integer statusCode, HttpStatus httpStatus, String message) {
}
