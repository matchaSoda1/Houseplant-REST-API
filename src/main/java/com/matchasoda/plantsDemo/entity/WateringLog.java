package com.matchasoda.plantsDemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="watering_log")
public class WateringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="date_watered")
    private LocalDate dateWatered;

    @Column(name="watering_status")
    private WateringStatus wateringStatus = WateringStatus.NOT_SET;

    //refers to the wateringLog property in the Plant class :)
    //so not anything in mysql!
    @JsonBackReference //prevents infinite json loop, in pair with @JsonManagedReference
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "wateringLog")
    private Plant plant;

    public WateringLog(){
    }



    public WateringLog(LocalDate dateWatered){
        this.dateWatered = dateWatered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateWatered() {
        return dateWatered;
    }

    public void setDateWatered(LocalDate dateWatered) {
        this.dateWatered = dateWatered;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public WateringStatus getWateringStatus() {
        return wateringStatus;
    }

    public void setWateringStatus(WateringStatus status) {
        this.wateringStatus = status;
    }
}