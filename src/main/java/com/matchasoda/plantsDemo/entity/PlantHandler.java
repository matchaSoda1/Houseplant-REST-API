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

            LocalDate nextWateringDate = getNextWateringDate(plant);

            if (nextWateringDate != null && nextWateringDate.isBefore(LocalDate.now())){
                plant.getWateringLog().setWateringStatus(WateringStatus.OVERDUE);
            } else if (nextWateringDate != null && nextWateringDate.isEqual(LocalDate.now())){
                plant.getWateringLog().setWateringStatus(WateringStatus.WATER_TODAY);
            }
            plantService.savePlant(plant);
        }
    }

    public LocalDate getNextWateringDate (Plant plant){
        LocalDate dateWatered = plant.getWateringLog().getDateWatered();
        if (dateWatered == null) {
            return null;
        }
        int wateringFrequency = plant.getWateringFrequency();

        return dateWatered.plus(Period.ofDays(wateringFrequency));
    }
}
