package com.custodio.vehiclefeature.adapter.controller;

import com.custodio.vehiclefeature.adapter.controller.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice("com.custodio.vehiclefeature.adapter.controller")
class GlobalControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    ErrorResponse handle(final RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    ErrorResponse handle(final EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    Collection<ErrorResponse> handle(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getConstraintViolations()
                       .stream()
                       .map(ConstraintViolation::getMessage)
                       .map(ErrorResponse::new)
                       .collect(toUnmodifiableList());
    }
}
