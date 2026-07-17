package com.matchasoda.plantsDemo.service;

import com.matchasoda.plantsDemo.dao.PlantRepository;
import com.matchasoda.plantsDemo.entity.Plant;
import com.matchasoda.plantsDemo.entity.PlantHandler;
import com.matchasoda.plantsDemo.entity.WateringLog;
import com.matchasoda.plantsDemo.entity.WateringStatus;
import com.matchasoda.plantsDemo.rest.PlantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
        plant.setWateringLog(new WateringLog());
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
        wateringLog.setWateringStatus(new PlantHandler().calculateWateringStatus(plant));

        plantRepository.save(plant);

        return plant;
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
}

