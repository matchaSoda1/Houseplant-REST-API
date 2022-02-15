package com.matchasoda.plantsDemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlantExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleNotFoundException(PlantNotFoundException e) {
        PlantErrorResponse errorResponse = new PlantErrorResponse(
                                                    HttpStatus.NOT_FOUND.value(),
                                                    e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleException(Exception e) {
        PlantErrorResponse errorResponse = new PlantErrorResponse(
                                                    HttpStatus.BAD_REQUEST.value(),
                                                    e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
