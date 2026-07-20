package com.matchasoda.plantTracker.dao;

import com.matchasoda.plantTracker.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Integer>{}
