package com.softserve.bot.exception.handlers;

import com.softserve.bot.exception.SubjectRestrictionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SubjectRestrictionExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({SubjectRestrictionException.class})
    protected ResponseEntity<Object> handleSubjectRestriction(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "The number of subjects is wrong(it must be between 3 and 5).", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
