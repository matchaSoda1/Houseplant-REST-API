package com.matchasoda.plantsDemo.rest;

import com.matchasoda.plantsDemo.entity.Plant;
import com.matchasoda.plantsDemo.entity.WateringRequest;
import com.matchasoda.plantsDemo.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plants")
public class PlantController {

    private PlantService plantService;
    private JsonMapper jsonMapper;

    @Autowired
    public PlantController(PlantService plantService, JsonMapper jsonMapper) {
        this.plantService = plantService;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping()
    public List<Plant> listAllPlants() {
        return plantService.listAllPlants();
    }

    @PostMapping
    public Plant addPlant(@RequestBody Plant plant) {
        plantService.savePlant(plant);
        return plant;
    }

    @PatchMapping("/{plantId}")
    public Plant updatePlant(@PathVariable int plantId, @RequestBody Map<String, Object> patchPayload) {
        Plant retrievedPlant = plantService.findPlantById(plantId);

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException(
                    "Cannot modify plant id. Remove 'id' from request body."
            );
        }

        Plant patchedPlant = jsonMapper.updateValue(retrievedPlant, patchPayload);

        plantService.updatePlant(patchedPlant);

        return patchedPlant;
    }

    @PatchMapping("water")
    public Plant waterPlant(@RequestBody WateringRequest wateringRequest) {

        int plantId = wateringRequest.getPlantId();
        LocalDate wateredDate = wateringRequest.getDateWatered();

        return plantService.waterPlant(plantId, wateredDate);
    }

    @PatchMapping("water/{plantId}")
    public Plant waterPlant(@PathVariable int plantId) {
        return plantService.waterPlant(plantId);
    }

    @DeleteMapping("/{plantId}")
    public List<Plant> deletePlant(@PathVariable int plantId) {
        plantService.deletePlantById(plantId);
        return plantService.listAllPlants();
    }

    @GetMapping("/water/overdue")
    public List<Plant> getOverduePlants() {
        return plantService.getOverduePlants();
    }
}
