package com.matchasoda.plantsDemo.rest;

import com.matchasoda.plantsDemo.entity.Plant;
import com.matchasoda.plantsDemo.entity.WateringLog;
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
    public List<Plant> listAllPlants() {
        return plantService.listAllPlants();
    }

    @PostMapping
    public Plant addPlant(@RequestBody Plant plant) {
        plant.setWateringLog(new WateringLog());
        plantService.savePlant(plant);
        return plant;

        //sample request [using class variable names NOT mysql variable names]
        //    {
        //        "name" : "Pilea",
        //        "wateringFrequency" : 7,
        //        "preferredBrightness" : "indirect bright",
        //        "soilMix" : "perlite, soil potting mix"
        //    }
    }

    @PutMapping
    public Plant updatePlant(@RequestBody Plant plant) {
        plantService.savePlant(plant);
        return plant;
    }

    @PutMapping("water")
    public Plant waterPlant(@RequestBody WateringRequest wateringRequest) {
        int plantId = wateringRequest.getPlantId();
        LocalDate wateredDate = wateringRequest.getDateWatered();

        return plantService.waterPlant(plantId, wateredDate);
    }

    @PutMapping("waterToday/{plantId}")
    public Plant waterPlant(@PathVariable int plantId) {

        //sample request:
        //{
        //  plantId:11,
        //  dateWatered: "2022-01-01"
        //}

        Plant plant = plantService.findPlantById(plantId);
        return plantService.waterPlant(plantId);
    }

    //
    @DeleteMapping("/{plantId}")
    public List<Plant> deletePlant(@PathVariable int plantId) {
        plantService.deletePlantById(plantId);
        return plantService.listAllPlants();
    }

    @GetMapping("/toWater")
    public List<Plant> plantsTowater() {
        return plantService.getPlantsToWater();
    }
}
