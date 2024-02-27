package com.cashier.system.skecobe.handlers.advice;

import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.responses.ResponseHandler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestAdviceController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations()
                .stream()
                .map(constraintViolation -> String.format("%s attribute %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                .collect(Collectors.toList());

        return ResponseHandler.errorResponse(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> responseException(ResponseStatusException e) {
        return ResponseHandler.errorResponse(
                Collections.singletonList(e.getReason()),
                (HttpStatus) e.getStatusCode()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException e) {
        return ResponseHandler.errorResponse(
                Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
