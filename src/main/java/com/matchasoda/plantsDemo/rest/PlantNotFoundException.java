package com.matchasoda.plantsDemo.rest;

public class PlantNotFoundException extends RuntimeException{

    public PlantNotFoundException(){

    }

    public PlantNotFoundException(String message) {
        super(message);
    }

    public PlantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlantNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlantNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
