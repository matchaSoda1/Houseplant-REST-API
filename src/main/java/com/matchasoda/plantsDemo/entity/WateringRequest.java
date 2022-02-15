package com.matchasoda.plantsDemo.entity;

import java.time.LocalDate;

public class WateringRequest {

    private int plantId;
    private LocalDate dateWatered;

    public WateringRequest() {
    }

    public WateringRequest(int plantId, LocalDate dateWatered) {
        this.plantId = plantId;
        this.dateWatered = dateWatered;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public LocalDate getDateWatered() {
        return dateWatered;
    }

    public void setWateredDate(LocalDate dateWatered) {
        this.dateWatered = dateWatered;
    }
}
