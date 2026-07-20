package com.matchasoda.plantTracker.rest;

public class PlantNotFoundException extends RuntimeException{

    public PlantNotFoundException(Integer plantId){
    super("Plant with id " + plantId + " was not found.");
    }

}
