package com.janluk.meeteevent.utils.exception;

import org.springframework.http.HttpStatus;

public record ExceptionAPI(Integer statusCode, HttpStatus httpStatus, String message) {
}
