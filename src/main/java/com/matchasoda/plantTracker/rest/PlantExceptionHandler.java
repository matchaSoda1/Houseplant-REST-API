package com.matchasoda.plantTracker.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlantExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleNotFoundException(PlantNotFoundException exception) {
        exception.printStackTrace();

        PlantErrorResponse error = new PlantErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleException(Exception exception) {
        exception.printStackTrace();

        PlantErrorResponse error = new PlantErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
