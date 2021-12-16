package com.softserve.bot.exception.handlers;

import com.softserve.bot.exception.SpecialtyNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SpecialtyNotFoundExceptionHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler({SpecialtyNotFoundException.class})
    protected ResponseEntity<Object> handleSpecialtyNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Specialty not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}