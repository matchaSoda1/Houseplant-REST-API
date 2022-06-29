package com.matchasoda.plantsDemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import org.hibernate.annotations.CascadeType.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="watering_frequency")
    private int wateringFrequency;

    @Column(name = "preferred_brightness")
    private String preferredBrightness;

    @Column(name="soil_mix")
    private String soilMix;

    //prevents infinite json loop on postman, paired with @JsonManagedReference
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="watering_log_id")
    private WateringLog wateringLog;

    public Plant(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getSoilMix() {
        return soilMix;
    }

    public void setSoilMix(String soilMix) {
        this.soilMix = soilMix;
    }

    public String getPreferredBrightness() {
        return preferredBrightness;
    }

    public void setPreferredBrightness(String preferredBrightness) {
        this.preferredBrightness = preferredBrightness;
    }

    public WateringLog getWateringLog() {
        return wateringLog;
    }

    public void setWateringLog(WateringLog wateringLog) {
        this.wateringLog = wateringLog;
    }
}
