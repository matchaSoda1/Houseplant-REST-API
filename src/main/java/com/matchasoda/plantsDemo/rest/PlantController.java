package com.matchasoda.plantsDemo.rest;

import com.matchasoda.plantsDemo.service.PlantService;
import com.matchasoda.plantsDemo.entity.Plant;
import com.matchasoda.plantsDemo.entity.WateringLog;
import com.matchasoda.plantsDemo.entity.WateringRequest;
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
    }

    @PutMapping
    public Plant updatePlant(@RequestBody Plant plant) {
        plantService.updatePlant(plant);
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
        return plantService.waterPlant(plantId);
    }

    @DeleteMapping("/{plantId}")
    public List<Plant> deletePlant(@PathVariable int plantId) {
        plantService.deletePlantById(plantId);
        return plantService.listAllPlants();
    }

    @GetMapping("/overdueWatering")
    public List<Plant> getOverduePlants() {
        return plantService.getOverduePlants();
    }
}
