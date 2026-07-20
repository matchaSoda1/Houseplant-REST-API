package com.matchasoda.plantTracker.service;

import com.matchasoda.plantTracker.entity.Plant;

import java.time.LocalDate;
import java.util.List;

public interface PlantService {

    public List<Plant> listAllPlants();

    public Plant findPlantById(int plantId);

    public Plant savePlant(Plant thePlant);

    public void updatePlant(Plant thePlant);

    public void deletePlantById(int plantId);

    public Plant waterPlant(int plantId, LocalDate date);

    public Plant waterPlant(int plantId);

    public List<Plant> getOverduePlants();
}
