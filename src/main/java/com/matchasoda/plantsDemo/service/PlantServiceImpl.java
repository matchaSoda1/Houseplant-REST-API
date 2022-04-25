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

    //lazy loading! if you want the watering log use the eager loading :)
    @Override
    public Plant getPlantById(int plantId) {
        return plantRepository.getById(plantId);
    }

    @Override
    public Plant findPlantById(int plantId) {
        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException(plantId);
        }
        return plantRepository.findById(plantId).get();
    }

    @Override
    public void savePlant(Plant thePlant) {
        plantRepository.save(thePlant);
    }
    @Override
    public void updatePlant(Plant thePlant) {
        //assumes request does not include waterLog
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
    public WateringLog getLogByPlant(int plantId) {

        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException(plantId);
        }

        Plant plant = plantRepository.findById(plantId).get();
        return plant.getWateringLog();
    }

    @Override
    public Plant waterPlant(int plantId) {
        return waterPlant(plantId, LocalDate.now());
    }

    @Override
    public Plant waterPlant(int plantId, LocalDate date) {

        if (!plantRepository.existsById(plantId)) {
            throw new PlantNotFoundException(plantId);
        }
//        Plant plant = plantRepository.getById(plantId); //lazy loading - throws an error
        Plant plant = plantRepository.findById(plantId).get(); //eager loading - gets all

        WateringLog wateringLog = plant.getWateringLog();
        wateringLog.setDateWatered(date);
        WateringStatus newWateringStatus =
                new PlantHandler().calculateWateringStatus(plant);
        wateringLog.setWateringStatus(newWateringStatus);

        plantRepository.save(plant);

        return plant;
    }

    @Override
    public List<Plant> getPlantsToWater() {
        List<Plant> plantsToWater = plantRepository.findAll();

        Iterator<Plant> iterator = plantsToWater.iterator();

        while (iterator.hasNext()) {
            Plant plant = iterator.next();
            if (!needsWatering(plant)) {
                iterator.remove();
            }
        }
        return plantsToWater;
    }

    private boolean needsWatering(Plant plant) {
        return plant.getWateringLog().getWateringStatus() != WateringStatus.WATERED;
    }


}

