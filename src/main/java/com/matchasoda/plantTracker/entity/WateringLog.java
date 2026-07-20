package com.matchasoda.plantTracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="watering_log")
public class WateringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="watering_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateWatered;

    @Enumerated(EnumType.STRING)
    @Column(name="watering_status")
    private WateringStatus wateringStatus = WateringStatus.NOT_SET;

    //refers to the wateringLog property in the Plant class, not the mysql table
    @JsonBackReference //prevents infinite json loop that shows on postman, in pair with @JsonManagedReference
    @OneToOne(mappedBy = "wateringLog")
    private Plant plant;

    public WateringLog() {
    }

    public WateringLog(LocalDate dateWatered){
        this.dateWatered = dateWatered;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateWatered() {
        return this.dateWatered;
    }

    public void setDateWatered(LocalDate dateWatered) {
        this.dateWatered = dateWatered;
    }

    public WateringStatus getWateringStatus() {
        return this.wateringStatus;
    }

    public void setWateringStatus(WateringStatus status) {
        this.wateringStatus = status;
    }
}
