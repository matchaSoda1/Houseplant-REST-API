package com.matchasoda.plantsDemo.entity;

import com.matchasoda.plantsDemo.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PlantHandler {

    @Autowired
    private PlantService plantService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void manageWateringStatus() {
        List<Plant> plantList = plantService.listAllPlants();

        for (Plant plant : plantList) {
            WateringStatus newWateringStatus = calculateWateringStatus(plant);
            plant.getWateringLog().setWateringStatus(newWateringStatus);
            plantService.savePlant(plant);
        }
    }

    public WateringStatus calculateWateringStatus(Plant plant){
        LocalDate dateWatered = plant.getWateringLog().getDateWatered();

        if (dateWatered == null) {
            return WateringStatus.NOT_SET;
        }

        LocalDate today = LocalDate.now();
        LocalDate nextWateringDate = getNextWateringDate(plant);

        if (nextWateringDate.isBefore(today)){
            return WateringStatus.OVERDUE;
        } else if (nextWateringDate.isEqual(today)){
            return WateringStatus.WATER_TODAY;
        }
        return WateringStatus.WATERED;
    }

    public LocalDate getNextWateringDate (Plant plant){
        LocalDate dateWatered = plant.getWateringLog().getDateWatered();
        int wateringFrequency = plant.getWateringFrequency();

        return dateWatered.plus(Period.ofDays(wateringFrequency));
    }
}
