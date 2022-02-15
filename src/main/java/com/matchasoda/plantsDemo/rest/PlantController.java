package com.matchasoda.plantsDemo.rest;

import com.matchasoda.plantsDemo.entity.Plant;
import com.matchasoda.plantsDemo.entity.WateringRequest;
import com.matchasoda.plantsDemo.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @GetMapping()
    public List<Plant> listAllPlants(){
        return plantService.listAllPlants();
    }

    @PostMapping
    public Plant addPlant(@RequestBody Plant plant) {
        plantService.savePlant(plant);
        return plant;

        //works!
        //send request like this, using class variable names NOT mysqltable variable names!
        //    {
        //        "name" : "Pilea",
        //        "wateringFrequency" : 7,
        //        "preferredBrightness" : "indirect bright",
        //        "soilMix" : "perlite, soil potting mix"
        //    }
    }

    @PutMapping
    public Plant updatePlant(@RequestBody Plant plant){
        plantService.savePlant(plant);
        return plant;
    }

    @PutMapping("water")
    public Plant waterPlant(@RequestBody WateringRequest wateringRequest){
        int plantId = wateringRequest.getPlantId();
        LocalDate wateredDate = wateringRequest.getDateWatered();

        Plant plant = plantService.findPlantById(plantId); //eager loading - but maybe shouldnt be because we're only
        //checking if the plant exists... :/

        if (plant == null) {
            throw new PlantNotFoundException("Plant with id " + plantId + " not found");
        }
        return plantService.waterPlant(plantId,wateredDate);
    }

    @PutMapping("water/{plantId}")
    public Plant waterPlant(@PathVariable int plantId){
        Plant plant = plantService.findPlantById(plantId);

        if (plant == null) {
            throw new PlantNotFoundException("Plant with id " + plantId + " not found");
        }

        return plantService.waterPlant(plantId);
    }
//
    @DeleteMapping("/{plantId}")
    public void deletePlant(@PathVariable int plantId){
        plantService.deletePlantById(plantId);
    }
//
    @GetMapping("/toWater")
//    make it return:
//    id
//    name
//    watering frequency
//    date watered
    public List<Plant> plantsTowater(){
        return plantService.getPlantsToWater();
    }
}
