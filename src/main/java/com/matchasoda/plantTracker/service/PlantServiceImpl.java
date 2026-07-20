package com.matchasoda.plantTracker.service;

import com.matchasoda.plantTracker.dao.PlantRepository;
import com.matchasoda.plantTracker.entity.Plant;
import com.matchasoda.plantTracker.entity.WateringLog;
import com.matchasoda.plantTracker.entity.WateringStatus;
import com.matchasoda.plantTracker.rest.PlantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PlantServiceImpl implements PlantService {

    private PlantRepository plantRepository;

    @Autowired
    public PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public List<Plant> listAllPlants() {
        return plantRepository.findAll();
    }

    @Override
    public Plant findPlantById(int plantId) {
        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException(plantId);
        }
        return plantRepository.findById(plantId).get();
    }

    @Override
    public Plant savePlant(Plant plant) {
        if (plant.getWateringLog() == null) {
            plant.setWateringLog(new WateringLog());
        }
        plant.getWateringLog().setWateringStatus(calculateWateringStatus(plant));
        return plantRepository.save(plant);
    }

    @Override
    public void updatePlant(Plant thePlant) {
        Plant plant = findPlantById(thePlant.getId());
        WateringLog wateringLog = plant.getWateringLog();
        thePlant.setWateringLog(wateringLog);

        plantRepository.save(thePlant);
    }

    @Override
    public void deletePlantById(int plantId) {
        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException(plantId);
        }
        plantRepository.deleteById(plantId);
    }

    @Override
    public Plant waterPlant(int plantId) {
        return waterPlant(plantId, LocalDate.now());
    }

    @Override
    public Plant waterPlant(int plantId, LocalDate date) {

        Plant plant = findPlantById(plantId);

        WateringLog wateringLog = plant.getWateringLog();
        wateringLog.setDateWatered(date);
        wateringLog.setWateringStatus(calculateWateringStatus(plant));

        plantRepository.save(plant);

        return plant;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void autoUpdateWateringStatus() {
        List<Plant> plantList = listAllPlants();

        for (Plant plant : plantList) {
            WateringStatus newWateringStatus = calculateWateringStatus(plant);
            plant.getWateringLog().setWateringStatus(newWateringStatus);
            savePlant(plant);
        }
    }

    @Override
    public List<Plant> getOverduePlants() {
        List<Plant> plantsToWater = plantRepository.findAll();

        Iterator<Plant> iterator = plantsToWater.iterator();

        while (iterator.hasNext()) {
            Plant plant = iterator.next();
            if (isWatered(plant)) {
                iterator.remove();
            }
        }
        return plantsToWater;
    }

    private boolean isWatered(Plant plant) {
        return plant.getWateringLog().getWateringStatus() == WateringStatus.WATERED;
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

