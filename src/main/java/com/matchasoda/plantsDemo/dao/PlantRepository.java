package com.matchasoda.plantsDemo.dao;

import com.matchasoda.plantsDemo.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlantRepository extends JpaRepository<Plant, Integer>{}
